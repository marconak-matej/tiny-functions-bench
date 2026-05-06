# Java Looping/Iteration Benchmarks

A comprehensive benchmark suite exploring **9 different iteration strategies** in Java, measuring the performance cost of various looping techniques across different data structures and list sizes.

## Implementations

The project includes 9 different iteration strategies:

1. **IndexedForLoopSum** - Classic indexed `for` loop with `get(i)` (O(1) on ArrayList, O(n) on LinkedList)
2. **EnhancedForLoopSum** - Enhanced `for` loop / for-each syntax (O(n), compiler-desugared Iterator)
3. **IterableForEachSum** - `Iterable.forEach()` with lambda (O(n), functional style)
4. **StreamForEachSum** - `Stream.forEach()` with lambda (O(n) + stream pipeline overhead)
5. **StreamReduceSum** - `Stream.mapToLong().sum()` (O(n), idiomatic, no boxing)
6. **ParallelStreamSum** - `parallelStream().mapToLong().sum()` (O(n/k) theoretical, poor in practice for small N)
7. **ExplicitIteratorSum** - Manual `Iterator` with while loop (O(n), what enhanced-for desugars to)
8. **ListIteratorSum** - `ListIterator` with while loop (O(n), bidirectional but unused here)
9. **ArrayConversionSum** - Convert to primitive `long[]` array, then indexed loop (O(n) conversion + O(n) iteration)

## Prerequisites

- Java 25 or higher
- Maven 3.x

## Building the Project

```bash
mvn clean package -pl looping
```

## Running the Tests

```bash
mvn test -pl looping
```

## Running the Benchmarks

```bash
java -Xms1g -Xmx1g -XX:+UseG1GC -jar looping/target/benchmarks.jar
```

### Benchmark Configuration

The benchmarks are configured with the following parameters:
- Benchmark Mode: Average Time (avgt)
- Output Time Unit: Nanoseconds
- Fork: 2 (1 warmup)
- Warmup: 3 iterations, 1 second each
- Measurement: 5 iterations, 1 second each

### Parameterized Test Configurations

- **N (number of elements):** 1,000, 10,000, 100,000
  - **N=1,000:** Baseline; all methods appear fast
  - **N=10,000:** Shows differences in iterator overhead
  - **N=100,000:** Highlights boxing cost and stream pipeline overhead

- **List Type:** `ArrayList`, `LinkedList`
  - **ArrayList:** Optimal for indexed access; LinkedList forces O(n) per `get()`
  - **LinkedList:** Favors iterator-based approaches; indexed access becomes prohibitive

## Expected Results

### Performance Champions

#### ArrayList @ 100,000 elements:
- **Indexed for loop**: Fastest (O(1) per get)
- **Enhanced for loop**: Very close (Iterator hidden by compiler)
- **ExplicitIterator**: Matches enhanced for loop
- **Array Conversion**: May compete or win depending on JIT optimization

#### LinkedList @ 100,000 elements:
- **Enhanced for loop**: Fastest (uses Iterator internally)
- **ExplicitIterator**: Matches enhanced for loop
- **Indexed for loop**: Catastrophically slow (O(n²) due to O(n) per get)
- **Array Conversion**: Overhead of stream conversion dominates

#### Stream Operations:
- **mapToLong().sum()**: Faster than forEach() (avoids boxing)
- **forEach()**: Slower (array wrapper workaround)
- **parallelStream()**: Slower than sequential at all tested sizes (fork-join overhead)

## Notable Implementation Details

### 1. Lambda Mutable Capture Workaround

The `IterableForEachSum` and `StreamForEachSum` implementations use:
```java
long[] sum = {0};
data.forEach(n -> sum[0] += n);
```

This is necessary because lambda expressions cannot capture mutable local variables. The array wrapper is effectively-final, allowing the lambda to modify its contents.

### 2. Stream Pipeline Fusion

`StreamReduceSum` uses `mapToLong(Long::longValue).sum()` which:
- Converts to `LongStream` (primitive specialization)
- Avoids boxing overhead
- Enables stream fusion optimizations

### 3. Array Conversion Strategy

`ArrayConversionSum` demonstrates a two-phase approach:
1. **Conversion phase**: `data.stream().mapToLong(Long::longValue).toArray()` (O(n))
2. **Iteration phase**: Tight indexed loop over primitive array (JIT-optimizable)

This trades upfront cost for potentially faster iteration with zero boxing and vectorization potential.

---
## Results

### ArrayList @ N=1,000

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| indexedForLoop | avgt | 10 | **263.944** 🥇 | ± 0.219 | ns/op |
| explicitIterator | avgt | 10 | **278.664** 🥈 | ± 6.223 | ns/op |
| enhancedForLoop | avgt | 10 | **281.518** 🥉 | ± 7.556 | ns/op |
| iterableForEach | avgt | 10 | 281.688 | ± 0.495 | ns/op |
| listIterator | avgt | 10 | 282.635 | ± 0.531 | ns/op |
| streamReduce | avgt | 10 | 290.475 | ± 2.878 | ns/op |
| streamForEach | avgt | 10 | 297.842 | ± 4.673 | ns/op |
| arrayConversion | avgt | 10 | 788.585 | ± 23.193 | ns/op |
| parallelStream | avgt | 10 | 15427.438 | ± 386.926 | ns/op |

**Key Insight:** Indexed for loop dominates on ArrayList at small sizes; parallelStream is 58x slower due to fork-join overhead.

### ArrayList @ N=10,000

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| indexedForLoop | avgt | 10 | **3159.020** 🥇 | ± 13.435 | ns/op |
| enhancedForLoop | avgt | 10 | **3181.360** 🥈 | ± 27.393 | ns/op |
| explicitIterator | avgt | 10 | **3176.885** 🥉 | ± 21.463 | ns/op |
| listIterator | avgt | 10 | 3210.698 | ± 84.865 | ns/op |
| iterableForEach | avgt | 10 | 3488.897 | ± 4.239 | ns/op |
| streamForEach | avgt | 10 | 4308.088 | ± 135.427 | ns/op |
| streamReduce | avgt | 10 | 4395.775 | ± 199.522 | ns/op |
| arrayConversion | avgt | 10 | 8805.513 | ± 51.556 | ns/op |
| parallelStream | avgt | 10 | 19636.151 | ± 933.581 | ns/op |

**Key Insight:** All iterator-based approaches cluster tightly (3159-3488 ns); Stream.forEach shows 36% overhead vs indexed loop.

### ArrayList @ N=100,000

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| indexedForLoop | avgt | 10 | **30832.517** 🥇 | ± 113.226 | ns/op |
| iterableForEach | avgt | 10 | **30600.881** 🥈 | ± 40.393 | ns/op |
| enhancedForLoop | avgt | 10 | **31746.598** 🥉 | ± 183.388 | ns/op |
| explicitIterator | avgt | 10 | 31759.985 | ± 238.512 | ns/op |
| listIterator | avgt | 10 | 31782.770 | ± 355.140 | ns/op |
| streamForEach | avgt | 10 | 31126.042 | ± 339.019 | ns/op |
| streamReduce | avgt | 10 | 35874.877 | ± 5958.130 | ns/op |
| parallelStream | avgt | 10 | 32212.927 | ± 375.048 | ns/op |
| arrayConversion | avgt | 10 | 109115.283 | ± 6803.211 | ns/op |

**Key Insight:** Iterables.forEach edges out indexed loop at scale; arrayConversion shows 3.5x overhead from conversion phase.

### LinkedList @ N=1,000

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| enhancedForLoop | avgt | 10 | **1518.197** 🥇 | ± 10.815 | ns/op |
| iterableForEach | avgt | 10 | **1490.417** 🥈 | ± 10.495 | ns/op |
| explicitIterator | avgt | 10 | **1525.053** 🥉 | ± 12.171 | ns/op |
| listIterator | avgt | 10 | 1530.246 | ± 11.892 | ns/op |
| streamReduce | avgt | 10 | 1608.975 | ± 14.961 | ns/op |
| streamForEach | avgt | 10 | 1607.678 | ± 1.889 | ns/op |
| arrayConversion | avgt | 10 | 1919.373 | ± 14.674 | ns/op |
| parallelStream | avgt | 10 | 20418.620 | ± 637.051 | ns/op |
| indexedForLoop | avgt | 10 | 273854.777 | ± 1334.064 | ns/op |

**Key Insight:** Iterator-based approaches (1490-1530 ns) are 180x faster than indexed for loop (273 μs) on LinkedList.

### LinkedList @ N=10,000

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| enhancedForLoop | avgt | 10 | **16659.732** 🥇 | ± 256.020 | ns/op |
| iterableForEach | avgt | 10 | **16791.470** 🥈 | ± 290.953 | ns/op |
| listIterator | avgt | 10 | **16728.575** 🥉 | ± 425.319 | ns/op |
| explicitIterator | avgt | 10 | 16833.402 | ± 314.257 | ns/op |
| streamForEach | avgt | 10 | 16898.132 | ± 162.324 | ns/op |
| streamReduce | avgt | 10 | 18768.379 | ± 360.250 | ns/op |
| arrayConversion | avgt | 10 | 23375.501 | ± 292.081 | ns/op |
| parallelStream | avgt | 10 | 41987.518 | ± 1131.977 | ns/op |
| indexedForLoop | avgt | 10 | 36934859.074 | ± 293143.162 | ns/op |

**Key Insight:** Indexed for loop becomes catastrophically slow (37 ms = O(n²)); stream conversion adds 40% overhead vs iterators.

### LinkedList @ N=100,000

| Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|
| enhancedForLoop | avgt | 10 | **159379.455** 🥇 | ± 1595.093 | ns/op |
| iterableForEach | avgt | 10 | **161399.320** 🥉 | ± 846.236 | ns/op |
| listIterator | avgt | 10 | **161313.256** 🥈 | ± 1130.010 | ns/op |
| explicitIterator | avgt | 10 | 160912.849 | ± 1086.672 | ns/op |
| streamForEach | avgt | 10 | 170403.223 | ± 756.847 | ns/op |
| streamReduce | avgt | 10 | 170832.830 | ± 1151.304 | ns/op |
| parallelStream | avgt | 10 | 272553.782 | ± 14133.834 | ns/op |
| arrayConversion | avgt | 10 | 219666.254 | ± 4486.122 | ns/op |
| indexedForLoop | avgt | 10 | 3859631454.200 | ± 62891697.057 | ns/op |

**Key Insight:** Indexed for loop on LinkedList at 100k is catastrophic (3.8 seconds = O(n²) disaster); avoid at all costs.