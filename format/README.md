# Java Order Summary Formatting Benchmarks

A comprehensive benchmark suite exploring **8 different approaches** to formatting order summary strings in Java, comparing different string formatting techniques with emphasis on **performance, thread-safety, and readability**.

## Problem Statement

In many Java applications, you need to format strings with both text interpolation and numeric formatting (e.g., prices with 2 decimal places). This benchmark compares different strategies for building strings like:
```
Order {orderId} for {customer}: ${amount}
```

The key challenge: How do you efficiently format the monetary amount while maintaining clean, readable code and ensuring thread-safety?

## Implementations

The project includes **8 different approaches**:

1. **ConcatenationImpl** - Plain `+` operator with `String.format()` (simple, readable, thread-safe)
2. **StringBuilderImpl** - `StringBuilder` + `String.format()` for amount (mutable, efficient)
3. **StringConcatWithDecimalFormatImpl** - `+` concatenation + `DecimalFormat` with `ThreadLocal` (avoids per-call formatting overhead)
4. **StringConcatWithNumberFormatImpl** - `+` concatenation + `NumberFormat` with `ThreadLocal` (locale-aware formatting)
5. **FormattedStringImpl** - `String.formatted()` (Java 15+, modern, concise, thread-safe)
6. **StringFormatImpl** - `String.format()` single call (concise, straightforward, thread-safe)
7. **FormatterImpl** - `java.util.Formatter` with try-with-resources (flexible, resource-managed)
8. **MessageFormatImpl** - `java.text.MessageFormat` with `ThreadLocal` (i18n-aware, complex pattern)

## Prerequisites

- Java 25 or higher (LTS)
- Maven 3.x

## Building the Project

```bash
mvn clean package
```

## Running the Tests

```bash
mvn test
```

## Running the Benchmarks

```bash
java -Xms1g -Xmx1g -XX:+UseG1GC -jar target/benchmarks.jar
```

### Benchmark Configuration

The benchmarks are configured with the following parameters:
- Benchmark Mode: Average Time (avgt)
- Output Time Unit: Nanoseconds
- Fork: 2 (1 warmup)
- Warmup: 3 iterations, 1 second each
- Measurement: 5 iterations, 1 second each

## Output Format

All implementations produce the exact same output format:
```
Order {orderId} for {customer}: ${amount}
```

Where `{amount}` is formatted to exactly 2 decimal places.

### Example

For input `("ORD-001", "John Doe", 99.9)` all implementations return:
```
Order ORD-001 for John Doe: $99.90
```

## Key Findings

### 1. **Single-Purpose String Methods Are Optimal**
The fastest implementations (`StringBuilder`, `Concatenation`) are **100-130 ns/op** because they:
- Use simple string building without formatting overhead
- Only call `String.format()` once for the amount
- Avoid unnecessary object allocation

### 2. **Format-Heavy Methods Have 2-3x Overhead**
Methods that use full formatting framework:
- `String.format()`: 186-268 ns/op (1.8-2.7x slower)
- `Formatter`: 215-257 ns/op (2.1-2.6x slower)
- `MessageFormat`: 237-315 ns/op (2.4-3.2x slower)

**Reason:** These methods parse format strings and manage complex type conversions on each call.

### 3. **ThreadLocal Caching Shows Inconsistent Benefit**
- `StringConcatWithDecimalFormat`: 98-160 ns/op (variable across amount sizes)
- `StringConcatWithNumberFormat`: 97-144 ns/op (more stable, fewer allocations at small values)
- Pure `+` concatenation: 94-109 ns/op (no overhead from SharedThreadLocal lookups)

### 4. **Variance Increases with Formatting Complexity**
- Simple approaches: ±0.6-5.5 ns error margin
- Format methods: ±4-32 ns error margin (up to 6x higher variance)
- Highest variance: `MessageFormat` at ±32.6 ns (on 1000.5 amount)

## Thread Safety

All implementations are **thread-safe**:
- **ConcatenationImpl**: Uses `String.format()` (immutable, thread-safe)
- **StringBuilderImpl**: Uses `StringBuilder` + `String.format()` (local instances only)
- **StringConcatWithDecimalFormatImpl**: Uses `ThreadLocal<DecimalFormat>` (thread-isolated)
- **StringConcatWithNumberFormatImpl**: Uses `ThreadLocal<NumberFormat>` (thread-isolated)
- **FormattedStringImpl**: Immutable `String.formatted()` (thread-safe)
- **StringFormatImpl**: Uses `String.format()` (immutable, thread-safe)
- **FormatterImpl**: Creates new `Formatter` on each call (no shared state)
- **MessageFormatImpl**: Uses `ThreadLocal<MessageFormat>` (thread-isolated)

## Results

All benchmarks measured in **nanoseconds (ns/op)** using JMH with the following parameters:
- Benchmark Mode: Average Time (avgt)
- Fork: 2 (1 warmup)
- Warmup: 3 iterations, 1 second each
- Measurement: 5 iterations, 1 second each
- Iterations: 10 measurements per configuration

### Amount Parameter: `99.9` (Two Decimal Places, Standard Case)

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| stringBuilder | avgt | 10 | **100.675** 🥇 | ± 2.000 | ns/op |
| concatenation | avgt | 10 | **109.125** 🥈 | ± 5.552 | ns/op |
| stringConcatWithDecimalFormat | avgt | 10 | **130.602** 🥉 | ± 9.964 | ns/op |
| stringConcatWithNumberFormat | avgt | 10 | 131.020 | ± 19.138 | ns/op |
| messageFormat | avgt | 10 | 246.799 | ± 25.712 | ns/op |
| formatter | avgt | 10 | 257.889 | ± 13.516 | ns/op |
| formattedString | avgt | 10 | 256.720 | ± 4.159 | ns/op |
| stringFormat | avgt | 10 | 268.588 | ± 4.844 | ns/op |

**Key Insight:** `StringBuilder` dominates at ~100 ns/op; format-specific methods (`String.format()`, `Formatter`, `MessageFormat`) are 2.4-2.7x slower.

### Amount Parameter: `1000.5` (Larger Magnitude)

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| concatenation | avgt | 10 | **107.768** 🥇 | ± 2.923 | ns/op |
| stringBuilder | avgt | 10 | **118.900** 🥈 | ± 5.571 | ns/op |
| stringConcatWithNumberFormat | avgt | 10 | **144.549** 🥉 | ± 19.022 | ns/op |
| stringConcatWithDecimalFormat | avgt | 10 | 160.167 | ± 17.864 | ns/op |
| stringFormat | avgt | 10 | 186.795 | ± 2.788 | ns/op |
| formattedString | avgt | 10 | 187.003 | ± 4.741 | ns/op |
| formatter | avgt | 10 | 215.916 | ± 8.868 | ns/op |
| messageFormat | avgt | 10 | 315.050 | ± 32.638 | ns/op |

**Key Insight:** `Concatenation` is fastest at ~108 ns/op; `MessageFormat` shows highest variance (±32.6 ns) and slowest performance at 315 ns.

### Amount Parameter: `0.01` (Minimum Decimal Value)

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| concatenation | avgt | 10 | **94.322** 🥇 | ± 3.192 | ns/op |
| stringConcatWithNumberFormat | avgt | 10 | **97.516** 🥈 | ± 3.106 | ns/op |
| stringConcatWithDecimalFormat | avgt | 10 | **98.567** 🥉 | ± 1.192 | ns/op |
| stringBuilder | avgt | 10 | 99.650 | ± 0.602 | ns/op |
| messageFormat | avgt | 10 | 237.900 | ± 25.146 | ns/op |
| formatter | avgt | 10 | 251.272 | ± 8.950 | ns/op |
| stringFormat | avgt | 10 | 268.355 | ± 10.590 | ns/op |
| formattedString | avgt | 10 | 269.193 | ± 7.842 | ns/op |

**Key Insight:** All simple approaches cluster tightly between 94-100 ns/op; format methods are consistently 2.5-2.8x slower.
