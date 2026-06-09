package io.github.mm.uuid.benchmark;

import io.github.mm.uuid.impl.*;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * JMH Benchmark for different UUID generation strategies.
 * Run with:
 *   mvn clean package
 *   java -jar target/benchmarks.jar
 *
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@State(Scope.Thread)
public class UUIDGenerationBenchmark {

    // Pre-created strategy instances
    private UUID1 uuid1;
    private UUID2 uuid2;
    private UUID3 uuid3;
    private UUID4 uuid4;
    private UUID5 uuid5;
    private UUID6 uuid6;
    private UUID7 uuid7;

    @Setup(Level.Trial)
    public void setup() {
        uuid1 = new UUID1();
        uuid2 = new UUID2();
        uuid3 = new UUID3();
        uuid4 = new UUID4();
        uuid5 = new UUID5();
        uuid6 = new UUID6();
        uuid7 = new UUID7();
    }

    @Benchmark
    public void uuidv1TimeBased(Blackhole bh) {
        bh.consume(uuid1.generateUUID());
    }

    @Benchmark
    public void uuidv2DceSecurity(Blackhole bh) {
        bh.consume(uuid2.generateUUID());
    }

    @Benchmark
    public void uuidv3NameBasedMd5(Blackhole bh) {
        bh.consume(uuid3.generateUUID());
    }

    @Benchmark
    public void uuidv4RandomBased(Blackhole bh) {
        bh.consume(uuid4.generateUUID());
    }

    @Benchmark
    public void uuidv5NameBasedSha1(Blackhole bh) {
        bh.consume(uuid5.generateUUID());
    }

    @Benchmark
    public void uuidv6TimeOrdered(Blackhole bh) {
        bh.consume(uuid6.generateUUID());
    }

    @Benchmark
    public void uuidv7TimeOrderedEpoch(Blackhole bh) {
        bh.consume(uuid7.generateUUID());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(UUIDGenerationBenchmark.class.getSimpleName())
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .forks(2)
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(5)
                .measurementTime(TimeValue.seconds(1))
                .build();

        new Runner(opt).run();
    }
}
