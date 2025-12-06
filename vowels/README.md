# Java Vowel Detection Benchmarks

A comprehensive benchmark suite exploring different approaches to detect vowels in strings using Java. This project was inspired by Austin Henley's exploration of vowel detection techniques.

## Overview

This project implements and benchmarks various approaches to detect vowels in strings, from classic loops to modern Java streams. Each implementation is thoroughly tested and benchmarked using JMH (Java Microbenchmark Harness).

## Performance Results

Here are the key findings from our benchmarks (in nanoseconds per operation):

### Top Performers
- **CharArray Implementation**: 1.296 ns/op (with vowels), 54.632 ns/op (without vowels)
- **ByteMask Implementation**: 1.297 ns/op (with vowels), 69.118 ns/op (without vowels)
- **BitSet Implementation**: 1.481 ns/op (with vowels), 64.707 ns/op (without vowels)

### Complete Benchmark Results

| Method           | withVowels (ns/op) | withoutVowels (ns/op) |
|------------------|--------------------|-----------------------|
| anyMatchContains | 30.301             | 858.955               |
| anyMatch         | 12.123             | 490.683               |
| bitSet           | 🟢 **1.481**       | 64.707                |
| byteMask         | 🟢 **1.297**       | 69.118                |
| charArray        | 🟢 **1.296**       | 🟢 **54.632**         |
| contains         | 15.513             | 157.358               |
| loopIn           | 8.972              | 211.650               |
| loopOr           | 8.242              | 174.308               |
| nestedFor        | 10.896             | 382.267               |
| recursion        | 1.743              | 🔴 **1555.377**       |
| regexReplace     | 🔴 **428.105**     | 118.510               |
| regex            | 14.023             | 75.144                |
| stringReplace    | 59.367             | 61.914                |

## Prerequisites

- Java 25 or higher
- Maven 3.x

## Building the Project

```bash
mvn clean package
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
- Warmup: 5 iterations, 1 second each
- Measurement: 10 iterations, 1 second each

## Project Structure

- `src/main/java/` - Main source code
- `src/test/java/` - Test cases
- `src/main/java/io/github/mm/vowels/checker/` - Vowel checker implementations
- `src/main/java/io/github/mm/vowels/benchmark/` - JMH benchmarks

## Dependencies

- JMH Core and Annotation Processor (v1.37)
- JUnit Jupiter (v5.9.2)

## Code Style

This project uses the Spotless Maven Plugin with Palantir Java Format for consistent code formatting. Format the code using:

```bash
mvn spotless:apply
```