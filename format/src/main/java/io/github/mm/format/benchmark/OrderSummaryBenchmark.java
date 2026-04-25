package io.github.mm.format.benchmark;

import io.github.mm.format.impl.*;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * JMH Benchmark for order summary formatter implementations.
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
public class OrderSummaryBenchmark {

    @Param({"99.9", "1000.5", "0.01"})
    public String amount;

    private FormattedStringImpl formattedStringImpl;
    private MessageFormatImpl messageFormatImpl;
    private StringBuilderImpl stringBuilderImpl;
    private ConcatenationWithDecimalFormatImpl concatenationWithDecimalFormatImpl;
    private ConcatenationWithNumberFormatImpl concatenationWithNumberFormatImpl;
    private FormatterImpl formatterImpl;
    private StringFormatImpl stringFormatImpl;
    private ConcatenationImpl concatenationImpl;

    @Setup(Level.Trial)
    public void setup() {
        formattedStringImpl = new FormattedStringImpl();
        messageFormatImpl = new MessageFormatImpl();
        stringBuilderImpl = new StringBuilderImpl();
        concatenationWithDecimalFormatImpl = new ConcatenationWithDecimalFormatImpl();
        concatenationWithNumberFormatImpl = new ConcatenationWithNumberFormatImpl();
        formatterImpl = new FormatterImpl();
        stringFormatImpl = new StringFormatImpl();
        concatenationImpl = new ConcatenationImpl();
    }

    @Benchmark
    public void formattedString(Blackhole bh) {
        bh.consume(formattedStringImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void messageFormat(Blackhole bh) {
        bh.consume(messageFormatImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void stringBuilder(Blackhole bh) {
        bh.consume(stringBuilderImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void stringConcatWithDecimalFormat(Blackhole bh) {
        bh.consume(concatenationWithDecimalFormatImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void stringConcatWithNumberFormat(Blackhole bh) {
        bh.consume(concatenationWithNumberFormatImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void formatter(Blackhole bh) {
        bh.consume(formatterImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void stringFormat(Blackhole bh) {
        bh.consume(stringFormatImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    @Benchmark
    public void concatenation(Blackhole bh) {
        bh.consume(concatenationImpl.buildOrderSummary("ORD-001", "John Doe", Double.parseDouble(amount)));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(OrderSummaryBenchmark.class.getSimpleName())
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

