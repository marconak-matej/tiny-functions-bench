package io.github.mm.concat.benchmark;

import io.github.mm.concat.impl.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * JMH Benchmark for string concatenation implementations.
 *
 * Run with:
 *   mvn clean package
 *   java -jar target/benchmarks.jar
 *
 * Or with specific parameters:
 *   java -jar target/benchmarks.jar -bm avgt -tu ns -f 2 -w 3 -i 5
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class ConcatBenchmark {

    @Param({"100", "1000", "10000"})
    public int n;

    @Param({"15", "25", "35"})
    public int stringLength;

    private List<String> items;

    // Pre-created concatenation instances
    private PlusOperatorConcat plusOperatorConcat;
    private StringConcatMethodConcat stringConcatMethodConcat;
    private StringFormatConcat stringFormatConcat;
    private StringBufferConcat stringBufferConcat;
    private StringBuilderConcat stringBuilderConcat;
    private StringBuilderWithCapacityConcat stringBuilderWithCapacityConcat;
    private StringJoinConcat stringJoinConcat;
    private CharArrayConcat charArrayConcat;
    private ByteBufferConcat byteBufferConcat;
    private StreamConcat streamConcat;
    private ParallelStreamConcat parallelStreamConcat;

    @Setup(Level.Trial)
    public void setup() {
        items = generateRandomStrings(n, stringLength);

        // Initialize all concat instances
        plusOperatorConcat = new PlusOperatorConcat();
        stringConcatMethodConcat = new StringConcatMethodConcat();
        stringFormatConcat = new StringFormatConcat();
        stringBufferConcat = new StringBufferConcat();
        stringBuilderConcat = new StringBuilderConcat();
        stringBuilderWithCapacityConcat = new StringBuilderWithCapacityConcat();
        stringJoinConcat = new StringJoinConcat();
        charArrayConcat = new CharArrayConcat();
        byteBufferConcat = new ByteBufferConcat();
        streamConcat = new StreamConcat();
        parallelStreamConcat = new ParallelStreamConcat();
    }

    private List<String> generateRandomStrings(int count, int length) {
        List<String> strings = new ArrayList<>();
        Random random = new Random(42L); // Fixed seed for reproducibility
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                sb.append((char) ('a' + random.nextInt(26)));
            }
            strings.add(sb.toString());
        }
        return strings;
    }

    @Benchmark
    public void plusOperator(Blackhole bh) {
        bh.consume(plusOperatorConcat.concat(items));
    }

    @Benchmark
    public void stringConcatMethod(Blackhole bh) {
        bh.consume(stringConcatMethodConcat.concat(items));
    }

    @Benchmark
    public void stringFormat(Blackhole bh) {
        bh.consume(stringFormatConcat.concat(items));
    }

    @Benchmark
    public void stringBuffer(Blackhole bh) {
        bh.consume(stringBufferConcat.concat(items));
    }

    @Benchmark
    public void stringBuilder(Blackhole bh) {
        bh.consume(stringBuilderConcat.concat(items));
    }

    @Benchmark
    public void stringBuilderWithCapacity(Blackhole bh) {
        bh.consume(stringBuilderWithCapacityConcat.concat(items));
    }

    @Benchmark
    public void stringJoin(Blackhole bh) {
        bh.consume(stringJoinConcat.concat(items));
    }

    @Benchmark
    public void charArray(Blackhole bh) {
        bh.consume(charArrayConcat.concat(items));
    }

    @Benchmark
    public void byteBuffer(Blackhole bh) {
        bh.consume(byteBufferConcat.concat(items));
    }

    @Benchmark
    public void stream(Blackhole bh) {
        bh.consume(streamConcat.concat(items));
    }

    @Benchmark
    public void parallelStream(Blackhole bh) {
        bh.consume(parallelStreamConcat.concat(items));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ConcatBenchmark.class.getSimpleName())
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

