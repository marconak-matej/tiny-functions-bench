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

### concat

Benchmarks different approaches to concatenate strings in Java.

- **Implementations**: 11 different approaches (`+` operator, `String.concat()`, `StringBuilder`, `String.join()`, `CharArray`, `ByteBuffer`, streams, etc.)
- **Test Cases**: Empty lists, single items, multiple items, special characters, Unicode, long strings
- **Datasets**: N = 100, 1,000, 10,000 with string lengths of 15, 25, 35 characters
- **Top Performers**: 
  - **CharArray**: 294 ns/op (N=100), 34,809 ns/op (N=10,000) - Fastest pure-Java approach
  - **StringBuilderWithCapacity**: 381 ns/op (N=100), 40,456 ns/op (N=10,000) - Practical choice with zero reallocations
  - **String.join()**: 465 ns/op (N=100), 49,853 ns/op (N=10,000) - Most idiomatic Java 8+
- **Key Finding**: Naive approaches (`+`, `.concat()`, `.format()`) show **1000x+ degradation** at scale (N=10,000)

See [concat/README.md](concat/README.md) for detailed information.

### looping

Benchmarks different approaches to iterate over collections in Java.

- **Implementations**: 9 different approaches (indexed for loop, enhanced for loop, Iterator, ListIterator, forEach, Stream, parallelStream, array conversion)
- **Test Configurations**: N = 1,000, 10,000, 100,000 with ArrayList and LinkedList
- **Top Performers**:
  - **ArrayList**: Indexed for loop (263 ns/op @ N=1k, 31k ns/op @ N=100k) - fastest with O(1) random access
  - **LinkedList**: Enhanced for loop / Iterator (1.5ms @ N=1k, 159μs @ N=100k) - avoids O(n²) catastrophe of indexed access
- **Key Finding**: Indexed for loop on LinkedList is catastrophically slow (3.8 seconds @ N=100k = O(n²) disaster); always use iterators for LinkedList
- **Key Finding**: parallelStream is 58x slower than sequential at small N due to fork-join overhead

See [looping/README.md](looping/README.md) for detailed information.
