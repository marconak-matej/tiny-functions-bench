# Java Palindrome Detection Benchmarks

A  benchmark suite exploring different approaches to detect if a string is a palindrome in Java.

## Implementations

The project includes 7 different approaches:

1. **TwoPointerPalindromeChecker** - Classic two-pointer approach from both ends
2. **StringBuilderPalindromeChecker** - Reverse string comparison using StringBuilder
3. **RegexPalindromeChecker** - Regex preprocessing to remove non-alphanumeric characters
4. **RecursivePalindromeChecker** - Recursive approach with left/right pointers
5. **HalfStringPalindromeChecker** - Only iterates through half the string
6. **StreamPalindromeChecker** - Java Streams with IntStream.range()
7. **CharArrayPalindromeChecker** - Converts to char array for fast access

## Validation Rules

### Standard Implementations (6 implementations)
- Accepts any single character as palindrome
- Case-sensitive: "Aba" is not a palindrome, but "aba" is
- Whitespace-sensitive: "a b a" is not a palindrome
- Rejects null or empty strings
- Special characters count: "!@#@!" is a palindrome

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

Each benchmark uses diverse datasets:

**Short Strings (5 characters):**
- Palindromes: "abcba", "level", "kayak"
- Non-palindromes: "hello", "world", "abcde"

**Medium Strings (20 characters):**
- Palindromes: Generated with symmetric patterns
- Non-palindromes: Random character sequences

**Long Strings (100 characters):**
- Palindromes: Large symmetric strings
- Non-palindromes: Large random strings

**Phrases:**
- "A man a plan a canal Panama"
- "race car"
- "Was it a car or a cat I saw"
- "hello world" (non-palindrome)

## Results

### Short Palindromes (5 characters)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| twoPointer | avgt | 10 | **5.912** 🥇 | ± 0.012 | ns/op |
| recursive | avgt | 10 | **5.369** 🥈 | ± 0.016 | ns/op |
| halfString | avgt | 10 | **6.228** 🥉 | ± 0.043 | ns/op |
| charArray | avgt | 10 | 13.186 | ± 0.241 | ns/op |
| stringBuilder | avgt | 10 | 27.047 | ± 12.792 | ns/op |
| stream | avgt | 10 | 29.416 | ± 0.671 | ns/op |

### Short Non-Palindromes (5 characters)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| twoPointer | avgt | 10 | **4.001** 🥇 | ± 0.036 | ns/op |
| halfString | avgt | 10 | **4.204** 🥈 | ± 0.036 | ns/op |
| recursive | avgt | 10 | **4.201** 🥉 | ± 0.462 | ns/op |
| charArray | avgt | 10 | 11.465 | ± 0.415 | ns/op |
| stream | avgt | 10 | 20.397 | ± 0.166 | ns/op |
| stringBuilder | avgt | 10 | 27.006 | ± 11.737 | ns/op |

### Medium Palindromes (20 characters)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| halfString | avgt | 10 | **9.781** 🥇 | ± 0.019 | ns/op |
| charArray | avgt | 10 | **13.565** 🥈 | ± 0.559 | ns/op |
| stringBuilder | avgt | 10 | **20.869** 🥉 | ± 0.412 | ns/op |
| twoPointer | avgt | 10 | 22.308 | ± 0.123 | ns/op |
| recursive | avgt | 10 | 22.581 | ± 0.033 | ns/op |
| stream | avgt | 10 | 87.758 | ± 1.950 | ns/op |

### Medium Non-Palindromes (20 characters)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| recursive | avgt | 10 | **3.943** 🥇 | ± 0.041 | ns/op |
| twoPointer | avgt | 10 | **4.062** 🥈 | ± 0.268 | ns/op |
| halfString | avgt | 10 | **4.202** 🥉 | ± 0.011 | ns/op |
| charArray | avgt | 10 | 7.499 | ± 0.102 | ns/op |
| stringBuilder | avgt | 10 | 20.415 | ± 0.063 | ns/op |
| stream | avgt | 10 | 20.464 | ± 0.131 | ns/op |

### Long Palindromes (100 characters)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| charArray | avgt | 10 | **31.579** 🥇 | ± 3.458 | ns/op |
| halfString | avgt | 10 | **32.786** 🥈 | ± 0.132 | ns/op |
| stringBuilder | avgt | 10 | **41.827** 🥉 | ± 0.226 | ns/op |
| twoPointer | avgt | 10 | 64.832 | ± 0.357 | ns/op |
| stream | avgt | 10 | 403.932 | ± 9.839 | ns/op |

### Long Non-Palindromes (100 characters)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| twoPointer | avgt | 10 | **4.239** 🥇 | ± 0.020 | ns/op |
| halfString | avgt | 10 | **4.425** 🥈 | ± 0.074 | ns/op |
| charArray | avgt | 10 | **12.149** 🥉 | ± 2.232 | ns/op |
| stream | avgt | 10 | 20.442 | ± 0.120 | ns/op |
| stringBuilder | avgt | 10 | 36.968 | ± 0.472 | ns/op |

### Phrases with Spaces (Regex Preprocessing)

| Implementation | Mode | Cnt | Score | Error | Units |
|----------------|------|-----|-------|-------|-------|
| regex | avgt | 10 | **229.443** | ± 2.013 | ns/op |

**Note:** Lower scores are better.

## Key Findings

1. **Non-Palindromes (Early Exit)**: TwoPointer, HalfString, and Recursive excel with ~4 ns/op due to early exit on first mismatch
2. **Short Strings**: Recursive and TwoPointer are fastest with minimal overhead
3. **Medium Strings**: HalfString wins for palindromes, while pointer-based methods dominate non-palindromes
4. **Long Strings**: CharArray and HalfString are fastest for palindromes, TwoPointer for non-palindromes
5. **StringBuilder**: Consistent ~20-40 ns/op across sizes but must complete full reverse
6. **Stream API**: Significant overhead (20-400 ns/op) makes it slowest for most cases
7. **Regex Preprocessing**: ~230 ns/op overhead for cleaning, only use when needed for natural language

### Performance Champions:

#### For Non-Palindromes (Early Exit):

* Two-Pointer: ~4ns - Unbeatable for quick rejection
* Recursive & Half-String also excellent at ~4ns

#### For Palindromes (Full Scan):

* Char Array: 31.6ns (long) - Best for longer strings
* Half-String: 9.8ns (medium) - Sweet spot for medium length
* Two-Pointer slower at 64.8ns for long strings due to repeated charAt() overhead