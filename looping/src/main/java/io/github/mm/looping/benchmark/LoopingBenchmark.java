package io.github.mm.looping.benchmark;

import io.github.mm.looping.impl.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * JMH Benchmark for different looping/iteration strategies.
 *
 * Run with:
 *   mvn clean package
 *   java -jar target/benchmarks.jar
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class LoopingBenchmark {

    @Param({"1000", "10000", "100000"})
    public int n;

    @Param({"ArrayList", "LinkedList"})
    public String listType;

    private List<Long> data;

    // Pre-created strategy instances
    private IndexedForLoopSum indexedForLoopSum;
    private EnhancedForLoopSum enhancedForLoopSum;
    private IterableForEachSum iterableForEachSum;
    private StreamForEachSum streamForEachSum;
    private StreamReduceSum streamReduceSum;
    private ParallelStreamSum parallelStreamSum;
    private ExplicitIteratorSum explicitIteratorSum;
    private ListIteratorSum listIteratorSum;
    private ArrayConversionSum arrayConversionSum;

    @Setup(Level.Trial)
    public void setup() {
        data = generateData(n, listType);

        indexedForLoopSum = new IndexedForLoopSum();
        enhancedForLoopSum = new EnhancedForLoopSum();
        iterableForEachSum = new IterableForEachSum();
        streamForEachSum = new StreamForEachSum();
        streamReduceSum = new StreamReduceSum();
        parallelStreamSum = new ParallelStreamSum();
        explicitIteratorSum = new ExplicitIteratorSum();
        listIteratorSum = new ListIteratorSum();
        arrayConversionSum = new ArrayConversionSum();
    }

    private List<Long> generateData(int count, String type) {
        List<Long> list = "ArrayList".equals(type) ? new ArrayList<>(count) : new LinkedList<>();
        for (long i = 0; i < count; i++) {
            list.add(i);
        }
        return list;
    }

    @Benchmark
    public void indexedForLoop(Blackhole bh) {
        bh.consume(indexedForLoopSum.sumElements(data));
    }

    @Benchmark
    public void enhancedForLoop(Blackhole bh) {
        bh.consume(enhancedForLoopSum.sumElements(data));
    }

    @Benchmark
    public void iterableForEach(Blackhole bh) {
        bh.consume(iterableForEachSum.sumElements(data));
    }

    @Benchmark
    public void streamForEach(Blackhole bh) {
        bh.consume(streamForEachSum.sumElements(data));
    }

    @Benchmark
    public void streamReduce(Blackhole bh) {
        bh.consume(streamReduceSum.sumElements(data));
    }

    @Benchmark
    public void parallelStream(Blackhole bh) {
        bh.consume(parallelStreamSum.sumElements(data));
    }

    @Benchmark
    public void explicitIterator(Blackhole bh) {
        bh.consume(explicitIteratorSum.sumElements(data));
    }

    @Benchmark
    public void listIterator(Blackhole bh) {
        bh.consume(listIteratorSum.sumElements(data));
    }

    @Benchmark
    public void arrayConversion(Blackhole bh) {
        bh.consume(arrayConversionSum.sumElements(data));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoopingBenchmark.class.getSimpleName())
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
