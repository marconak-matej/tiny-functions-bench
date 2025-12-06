# Java Integer Detection Benchmarks

A comprehensive benchmark suite exploring different approaches to detect if a string represents a valid integer in Java. This project was inspired by the vowels detection project and follows a similar structure.

## Overview

This project implements and benchmarks various approaches to validate if a string represents a valid Java integer. Each implementation is thoroughly tested and benchmarked using JMH (Java Microbenchmark Harness).

## Implementations

The project includes 10 different approaches:

1. **TryCatchIntegerChecker** - Simple try-catch with Integer.parseInt()
2. **OptimizedTryCatchIntegerChecker** - Try-catch with pre-validation checks
3. **RegexIntegerChecker** - Compiled regex pattern matching
4. **MatchesIntegerChecker** - String.matches() with regex
5. **ManualParsingIntegerChecker** - Manual character-by-character parsing with charAt()
6. **CharAtIntegerChecker** - Similar to manual parsing but optimized
7. **CharArrayIntegerChecker** - Using char[] array for parsing
8. **StreamIntegerChecker** - Java Streams with allMatch()
9. **ScannerIntegerChecker** - Using java.util.Scanner
10. **ApacheCommonsIntegerChecker** - Character.isDigit() approach

## Validation Rules

All implementations follow these rules:
- Accepts integers in the range: -2,147,483,648 to 2,147,483,647 (Java int range)
- Allows optional leading + or - sign
- Allows leading zeros (e.g., "007")
- Rejects null, empty, or whitespace-only strings
- Rejects strings with spaces before, after, or within digits
- Rejects decimal points, scientific notation, or other formats

## Prerequisites

- Java 25 or higher
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
- Benchmark Mode: Average Time
- Output Time Unit: Nanoseconds
- Fork: 2 (1 warmup)
- Warmup: 3 iterations, 1 second each
- Measurement: 5 iterations, 1 second each
- Dataset Size: 10,000 pre-generated test strings per category

### Dataset Characteristics

Each benchmark uses a diverse dataset of 10,000 test strings to ensure realistic performance measurements:

**Valid Integers:**
- Random integers from 0 to 999,999
- Large integers near Integer.MAX_VALUE (2,147,483,647)

**Invalid Inputs (8 different types):**
- Alphabetic prefix: "abc123"
- Alphabetic suffix: "123xyz"
- Decimal numbers: "12.34"
- Leading whitespace: " 123"
- Trailing whitespace: "123 "
- Out-of-range values: "9999999999"
- Scientific notation: "123e10"
- Mixed alphanumeric: "abc123def"

The benchmarks rotate through the dataset using round-robin access, ensuring that each invocation tests a different input string. This provides more realistic and stable performance measurements compared to testing the same string repeatedly.

## Project Structure

- `src/main/java/` - Main source code
- `src/test/java/` - Test cases
- `src/main/java/io/github/mm/isinteger/checker/` - IntegerChecker interface
- `src/main/java/io/github/mm/isinteger/checker/impl/` - Implementation classes
- `src/main/java/io/github/mm/isinteger/benchmark/` - JMH benchmarks

## Test Scenarios

The test suite covers:
- Valid integers (positive, negative, zero)
- Invalid inputs (null, empty, non-numeric, decimals)
- Boundary values (Integer.MAX_VALUE, Integer.MIN_VALUE)
- Out-of-range values
- Edge cases (leading zeros, signed numbers)
- Whitespace handling

## Dependencies

- JMH Core and Annotation Processor (v1.37)
- JUnit Jupiter (v5.9.2)

## Code Style

This project uses the Spotless Maven Plugin with Palantir Java Format for consistent code formatting. Format the code using:

```bash
mvn spotless:apply
```

## Performance Results

Run the benchmarks to see which approach performs best for your use case. Results will vary based on:
- Input string characteristics (length, validity)
- JVM version and configuration
- Hardware platform

## Results

### Valid Integers

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| tryCatch | avgt | 10 | **9.214** 🥇 | ± 0.634 | ns/op |
| optimizedTryCatch | avgt | 10 | **9.508** 🥈 | ± 1.167 | ns/op |
| manualParsing | avgt | 10 | **9.697** 🥉 | ± 1.200 | ns/op |
| charAt | avgt | 10 | 9.954 | ± 0.703 | ns/op |
| apacheCommons | avgt | 10 | 12.309 | ± 0.091 | ns/op |
| charArray | avgt | 10 | 18.352 | ± 0.118 | ns/op |
| stream | avgt | 10 | 19.657 | ± 0.132 | ns/op |
| regex | avgt | 10 | 26.540 | ± 2.055 | ns/op |
| matches | avgt | 10 | 92.785 | ± 5.755 | ns/op |
| scanner | avgt | 10 | 4917.192 | ± 300.571 | ns/op |

### Invalid Inputs

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| charAt | avgt | 10 | **6.496** 🥇 | ± 0.701 | ns/op |
| manualParsing | avgt | 10 | **6.866** 🥈 | ± 0.965 | ns/op |
| charArray | avgt | 10 | **17.162** 🥉 | ± 0.599 | ns/op |
| apacheCommons | avgt | 10 | 110.687 | ± 3.091 | ns/op |
| stream | avgt | 10 | 121.776 | ± 9.986 | ns/op |
| regex | avgt | 10 | 138.858 | ± 15.109 | ns/op |
| matches | avgt | 10 | 203.260 | ± 13.740 | ns/op |
| optimizedTryCatch | avgt | 10 | 536.795 | ± 27.884 | ns/op |
| tryCatch | avgt | 10 | 822.446 | ± 30.727 | ns/op |
| scanner | avgt | 10 | 3969.875 | ± 347.900 | ns/op |

### Large Integers

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| tryCatch | avgt | 10 | **12.411** 🥇 | ± 0.399 | ns/op |
| manualParsing | avgt | 10 | **12.976** 🥈 | ± 0.359 | ns/op |
| charAt | avgt | 10 | **13.125** 🥉 | ± 1.243 | ns/op |
| optimizedTryCatch | avgt | 10 | 13.171 | ± 1.631 | ns/op |
| apacheCommons | avgt | 10 | 15.939 | ± 0.635 | ns/op |
| charArray | avgt | 10 | 16.025 | ± 1.527 | ns/op |
| stream | avgt | 10 | 25.304 | ± 1.282 | ns/op |
| regex | avgt | 10 | 31.074 | ± 2.453 | ns/op |
| matches | avgt | 10 | 108.411 | ± 31.432 | ns/op |
| scanner | avgt | 10 | 4925.757 | ± 388.066 | ns/op |

**Note:** Lower scores are better. 🥇 = 1st place, 🥈 = 2nd place, 🥉 = 3rd place in each category.
