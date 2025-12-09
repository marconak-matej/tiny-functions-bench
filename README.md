# Tiny Functions Benchmark Suite

A collection of benchmark projects exploring different implementations of tiny utility functions in Java. Each module focuses on a specific common programming task and benchmarks various approaches to implement it.

## Overview

This project aims to provide comprehensive performance analysis of different implementation strategies for common utility functions. Each module includes:

- Multiple implementation approaches
- Comprehensive unit tests
- JMH (Java Microbenchmark Harness) benchmarks
- Performance results and analysis

## Modules

### is-integer

Benchmarks different approaches to validate if a string represents a valid Java integer.

- **Implementations**: 10 different approaches (try-catch, regex, manual parsing, streams, etc.)
- **Test Cases**: Valid integers, invalid inputs, boundary values, edge cases
- **Dataset**: 10,000 pre-generated test strings per benchmark
- **Top Performers**: Manual parsing and charAt methods (6-13 ns/op)

See [is-integer/README.md](is-integer/README.md) for detailed information.

### vowels

Benchmarks different approaches to detect vowels in strings.

- **Implementations**: 13 different approaches (loops, regex, streams, bit operations, etc.)
- **Test Cases**: Strings with vowels, strings without vowels
- **Top Performers**: CharArray, ByteMask, and BitSet implementations (1.3-1.5 ns/op for strings with vowels)

See [vowels/README.md](vowels/README.md) for detailed information.

### is-palindrome

Benchmarks different approaches to check if a string is a palindrome.

- **Implementations**: 7 different approaches (two-pointer, StringBuilder, regex, recursive, streams, etc.)
- **Test Cases**: Short/medium/long palindromes, case sensitivity, whitespace handling, special characters
- **Dataset**: 10,000 pre-generated test strings across multiple length categories
- **Expected Top Performers**: Two-pointer and half-string methods for O(1) space efficiency

See [is-palindrome/README.md](is-palindrome/README.md) for detailed information.

