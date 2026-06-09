# UUID Generation Performance Benchmark

This module benchmarks the performance of different UUID generation strategies using the [uuid-creator](https://mvnrepository.com/artifact/com.github.f4b6a3/uuid-creator) library.

## UUID Versions Benchmarked

- **UUID v1 (Time-based)**: Generates a UUID based on the current timestamp and MAC address
- **UUID v2 (DCE Security)**: Generates a UUID based on DCE security features (timestamp + local domain)
- **UUID v3 (Name-based MD5)**: Generates a UUID from a namespace and name using MD5 hashing
- **UUID v4 (Random-based)**: Generates a random UUID
- **UUID v5 (Name-based SHA1)**: Generates a UUID from a namespace and name using SHA1 hashing
- **UUID v6 (Time-ordered)**: Generates a time-ordered UUID (sortable variant of v1)
- **UUID v7 (Time-ordered Epoch)**: Generates a time-ordered UUID with Unix epoch timestamp (RFC 9562)

## Prerequisites

- Java 25 or higher
- Maven 3.x

## Building the Project

```bash
mvn clean package -pl uuid
```

## Running the Tests

```bash
mvn test -pl uuid

## Running the Benchmark

### Build the project
mvn clean package
```

### Run the benchmark
```bash
java -Xms1g -Xmx1g -XX:+UseG1GC -jar uuid/target/benchmarks.jar
```

### Benchmark Configuration

The benchmarks are configured with the following parameters:
- Benchmark Mode: Average Time (avgt)
- Output Time Unit: Nanoseconds
- Fork: 5 (1 warmup)
- Warmup: 10 iterations, 1 second each
- Measurement: 10 iterations, 1 second each


## Implementation Details

Each UUID generation strategy is implemented as a separate class implementing the `UUIDGenerationStrategy` interface. This allows for easy performance comparison between different UUID versions and generation methods.

## Expected Results

The benchmark uses JMH (Java Microbenchmark Harness) for accurate performance measurements with proper warmup and measurement phases.

- **Expected Performance Characteristics**:
    - **Deterministic UUIDs** (v3, v5): Slower due to hashing operations (MD5, SHA1)
    - **Random-based** (v4): Fast, uses random generation
    - **Time-based** (v1, v6, v7): Fast, use timestamp-based generation
    - **DCE Security** (v2): Similar to time-based with security features

## Results

### Performance Summary - AverageTime (Mode.AverageTime)

| UUID Version | Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|---|
| **v6** | Time-ordered | avgt | 20 | **22.410** 🥇 | ± 0.053 | ns/op |
| **v1** | Time-based | avgt | 20 | **23.346** 🥈 | ± 0.227 | ns/op |
| **v2** | DCE Security | avgt | 20 | **25.425** 🥉 | ± 0.196 | ns/op |
| **v5** | Name-based SHA1 | avgt | 20 | 55.102 | ± 0.108 | ns/op |
| **v7** | Time-ordered Epoch | avgt | 20 | 85.475 | ± 1.525 | ns/op |
| **v3** | Name-based MD5 | avgt | 20 | 102.849 | ± 0.507 | ns/op |
| **v4** | Random-based | avgt | 20 | 104.995 | ± 0.319 | ns/op |

### Performance Summary - Throughput (Mode.Throughput)

| UUID Version | Implementation | Mode | Cnt | Score | Error | Units |
|---|---|---|---|---|---|---|
| **v6** | Time-ordered | thrpt | 20 | **0.044** 🥇 | ± 0.001 | ops/ns |
| **v1** | Time-based | thrpt | 20 | **0.043** 🥈 | ± 0.001 | ops/ns |
| **v2** | DCE Security | thrpt | 20 | **0.040** 🥉 | ± 0.001 | ops/ns |
| **v5** | Name-based SHA1 | thrpt | 20 | 0.018 | ± 0.001 | ops/ns |
| **v7** | Time-ordered Epoch | thrpt | 20 | 0.012 | ± 0.001 | ops/ns |
| **v3** | Name-based MD5 | thrpt | 20 | 0.010 | ± 0.001 | ops/ns |
| **v4** | Random-based | thrpt | 20 | 0.010 | ± 0.001 | ops/ns |

### Key Insights

#### Performance Tiers (AverageTime)

**Tier 1: Fast (22-25 ns/op)** 
- **UUID v6 (Time-ordered)**: Fastest overall at 22.410 ns/op
- **UUID v1 (Time-based)**: Virtually identical to v6 at 23.346 ns/op
- **UUID v2 (DCE Security)**: Slightly slower at 25.425 ns/op (minimal overhead from security context)

These timestamp-based generators are extremely fast because they rely on simple timestamp operations and don't require cryptographic operations.

**Tier 2: Moderate (55 ns/op)** 
- **UUID v5 (Name-based SHA1)**: 55.102 ns/op

SHA1-based generation is ~2.5x slower than timestamp-based methods due to hashing operations, but still very reasonable.

**Tier 3: Slow (85-105 ns/op)** 
- **UUID v7 (Time-ordered Epoch)**: 85.475 ns/op
- **UUID v3 (Name-based MD5)**: 102.849 ns/op
- **UUID v4 (Random-based)**: 104.995 ns/op

MD5 and random-based generation are the slowest at 4.7x the cost of timestamp-based methods. v7, while time-ordered, has additional epoch calculation overhead.