# String Reversal Performance Benchmark

This module benchmarks the performance of different string reversal strategies in Java.

## Strategies Benchmarked

| # | Strategy | Description |
|---|----------|-------------|
| 1 | **StringBuilder** | Uses `new StringBuilder(s).reverse().toString()` — idiomatic, handles surrogate pairs |
| 2 | **TwoPointer** | `char[]` with two-pointer in-place swap — fastest, breaks non-BMP characters |
| 3 | **Loop** | Backwards loop with `StringBuilder.append` — simple and fast |
| 4 | **Recursion** | Recursive `substring(1) + charAt(0)` — O(n²), educational only |
| 5 | **Stream (codePoints)** | Uses `codePoints()` with `Collectors.toList` + `Collections.reverse` — codepoint-safe but slow |
| 6 | **Stream (index)** | Uses `IntStream.range()` with index-based reverse access — functional style |
| 7 | **Stack** | Uses `ArrayDeque` as a stack — push all chars, then pop |
| 8 | **Collections** | Uses `Collections.reverse(List<Character>)` — boxed Character objects |

## Prerequisites

- Java 25 or higher
- Maven 3.x

## Building the Project

```bash
mvn clean package -pl reverse
```

## Running the Tests

```bash
mvn test -pl reverse
```

## Running the Benchmark

### Build the project
```bash
mvn clean package -pl reverse
```

### Run the benchmark
```bash
java -Xms1g -Xmx1g -XX:+UseG1GC -jar reverse/target/benchmarks.jar
```

### Benchmark Configuration

The benchmarks are configured with the following parameters:
- Benchmark Mode: Average Time (avgt)
- Output Time Unit: Nanoseconds
- Fork: 5 (1 warmup)
- Warmup: 10 iterations, 1 second each
- Measurement: 10 iterations, 1 second each
- Three string sizes: short (5 chars), medium (43 chars), long (123 chars)

## Implementation Details

Each reversal strategy is implemented as a separate class implementing the `ReverseStrategy` interface. This allows for easy performance comparison between different approaches.

## Results

The benchmark results grouped by input size. Lower scores are better (ns/op).

### Short Strings (short)

| Implementation | Mode | Cnt | Score (ns/op) | Error |
|----------------|------|-----:|--------------:|------:|
| twoPointer | avgt | 10 | **6.076** 🥇 | ± 0.692 |
| stringBuilder | avgt | 10 | **7.224** 🥈 | ± 0.119 |
| loop | avgt | 10 | **8.046** 🥉 | ± 0.380 |
| recursive | avgt | 10 | 32.703 | ± 0.294 |
| stack | avgt | 10 | 39.400 | ± 2.235 |
| streamIndex | avgt | 10 | 23.855 | ± 0.807 |
| streamCodePoints | avgt | 10 | 44.826 | ± 0.507 |
| collections | avgt | 10 | 57.016 | ± 0.942 |

### Medium Strings (medium)

| Implementation | Mode | Cnt | Score (ns/op) | Error |
|----------------|------|-----:|--------------:|------:|
| stringBuilder | avgt | 10 | **14.135** 🥇 | ± 0.791 |
| twoPointer | avgt | 10 | **17.844** 🥈 | ± 6.410 |
| streamIndex | avgt | 10 | 113.001 | ± 1.736 |
| loop | avgt | 10 | **63.385** 🥉 | ± 0.640 |
| collections | avgt | 10 | 163.691 | ± 3.580 |
| streamCodePoints | avgt | 10 | 338.673 | ± 1.587 |
| stack | avgt | 10 | 363.722 | ± 4.991 |
| recursive | avgt | 10 | 408.710 | ± 2.858 |

### Long Strings (long)

| Implementation | Mode | Cnt | Score (ns/op) | Error |
|----------------|------|-----:|--------------:|------:|
| stringBuilder | avgt | 10 | **27.484** 🥇 | ± 0.373 |
| twoPointer | avgt | 10 | **31.739** 🥈 | ± 3.636 |
| loop | avgt | 10 | **128.403** 🥉 | ± 8.683 |
| streamIndex | avgt | 10 | 272.312 | ± 0.883 |
| collections | avgt | 10 | 450.989 | ± 3.106 |
| streamCodePoints | avgt | 10 | 1172.191 | ± 4.179 |
| stack | avgt | 10 | 1011.617 | ± 17.131 |
| recursive | avgt | 10 | 1605.489 | ± 31.431 |

## Key Findings

- StringBuilder is the best general-purpose strategy: fastest for medium and long inputs and also performs very well for short inputs.
- Two-pointer char[] approach is extremely fast for short inputs and competitive on long inputs, but may mishandle non-BMP characters (surrogate pairs).
- Loop/backwards-append is a simple and efficient alternative, especially for short/medium sizes.
- Stream-based and boxed/collection approaches are significantly slower; prefer them only when codepoint-safety or readability outweighs performance.

*All numbers are from JMH runs in this module. Units: ns/op.*