package io.github.mm.reverse.benchmark;

import io.github.mm.reverse.impl.*;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class ReverseBenchmark {

    private ReverseStringBuilder stringBuilder;
    private ReverseTwoPointer twoPointer;
    private ReverseLoop loop;
    private ReverseRecursive recursive;
    private ReverseStreamCodePoints streamCodePoints;
    private ReverseStreamIndex streamIndex;
    private ReverseStack stack;
    private ReverseCollections collections;

    private String shortString;
    private String mediumString;
    private String longString;

    @Setup(Level.Trial)
    public void setup() {
        stringBuilder = new ReverseStringBuilder();
        twoPointer = new ReverseTwoPointer();
        loop = new ReverseLoop();
        recursive = new ReverseRecursive();
        streamCodePoints = new ReverseStreamCodePoints();
        streamIndex = new ReverseStreamIndex();
        stack = new ReverseStack();
        collections = new ReverseCollections();

        shortString = "hello";
        mediumString = "The quick brown fox jumps over the lazy dog";
        longString =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    }

    @Benchmark
    public void stringBuilderShort(Blackhole bh) {
        bh.consume(stringBuilder.reverse(shortString));
    }

    @Benchmark
    public void stringBuilderMedium(Blackhole bh) {
        bh.consume(stringBuilder.reverse(mediumString));
    }

    @Benchmark
    public void stringBuilderLong(Blackhole bh) {
        bh.consume(stringBuilder.reverse(longString));
    }

    @Benchmark
    public void twoPointerShort(Blackhole bh) {
        bh.consume(twoPointer.reverse(shortString));
    }

    @Benchmark
    public void twoPointerMedium(Blackhole bh) {
        bh.consume(twoPointer.reverse(mediumString));
    }

    @Benchmark
    public void twoPointerLong(Blackhole bh) {
        bh.consume(twoPointer.reverse(longString));
    }

    @Benchmark
    public void loopShort(Blackhole bh) {
        bh.consume(loop.reverse(shortString));
    }

    @Benchmark
    public void loopMedium(Blackhole bh) {
        bh.consume(loop.reverse(mediumString));
    }

    @Benchmark
    public void loopLong(Blackhole bh) {
        bh.consume(loop.reverse(longString));
    }

    @Benchmark
    public void recursiveShort(Blackhole bh) {
        bh.consume(recursive.reverse(shortString));
    }

    @Benchmark
    public void recursiveMedium(Blackhole bh) {
        bh.consume(recursive.reverse(mediumString));
    }

    @Benchmark
    public void recursiveLong(Blackhole bh) {
        bh.consume(recursive.reverse(longString));
    }

    @Benchmark
    public void streamCodePointsShort(Blackhole bh) {
        bh.consume(streamCodePoints.reverse(shortString));
    }

    @Benchmark
    public void streamCodePointsMedium(Blackhole bh) {
        bh.consume(streamCodePoints.reverse(mediumString));
    }

    @Benchmark
    public void streamCodePointsLong(Blackhole bh) {
        bh.consume(streamCodePoints.reverse(longString));
    }

    @Benchmark
    public void streamIndexShort(Blackhole bh) {
        bh.consume(streamIndex.reverse(shortString));
    }

    @Benchmark
    public void streamIndexMedium(Blackhole bh) {
        bh.consume(streamIndex.reverse(mediumString));
    }

    @Benchmark
    public void streamIndexLong(Blackhole bh) {
        bh.consume(streamIndex.reverse(longString));
    }

    @Benchmark
    public void stackShort(Blackhole bh) {
        bh.consume(stack.reverse(shortString));
    }

    @Benchmark
    public void stackMedium(Blackhole bh) {
        bh.consume(stack.reverse(mediumString));
    }

    @Benchmark
    public void stackLong(Blackhole bh) {
        bh.consume(stack.reverse(longString));
    }

    @Benchmark
    public void collectionsShort(Blackhole bh) {
        bh.consume(collections.reverse(shortString));
    }

    @Benchmark
    public void collectionsMedium(Blackhole bh) {
        bh.consume(collections.reverse(mediumString));
    }

    @Benchmark
    public void collectionsLong(Blackhole bh) {
        bh.consume(collections.reverse(longString));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ReverseBenchmark.class.getSimpleName())
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
