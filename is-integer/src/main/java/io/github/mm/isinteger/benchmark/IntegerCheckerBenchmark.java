package io.github.mm.isinteger.benchmark;

import io.github.mm.isinteger.checker.impl.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
public class IntegerCheckerBenchmark {
    private static final Random random = new Random(42);
    private static final int DATASET_SIZE = 10000;

    private List<String> validIntegers;
    private List<String> invalidIntegers;
    private List<String> largeValidIntegers;
    private int index = 0;

    private TryCatchIntegerChecker tryCatchChecker;
    private RegexIntegerChecker regexChecker;
    private ManualParsingIntegerChecker manualParsingChecker;
    private CharArrayIntegerChecker charArrayChecker;
    private StreamIntegerChecker streamChecker;
    private OptimizedTryCatchIntegerChecker optimizedTryCatchChecker;
    private ScannerIntegerChecker scannerChecker;
    private MatchesIntegerChecker matchesChecker;
    private CharAtIntegerChecker charAtChecker;
    private ApacheCommonsIntegerChecker apacheCommonsChecker;

    @Setup
    public void setup() {
        tryCatchChecker = new TryCatchIntegerChecker();
        regexChecker = new RegexIntegerChecker();
        manualParsingChecker = new ManualParsingIntegerChecker();
        charArrayChecker = new CharArrayIntegerChecker();
        streamChecker = new StreamIntegerChecker();
        optimizedTryCatchChecker = new OptimizedTryCatchIntegerChecker();
        scannerChecker = new ScannerIntegerChecker();
        matchesChecker = new MatchesIntegerChecker();
        charAtChecker = new CharAtIntegerChecker();
        apacheCommonsChecker = new ApacheCommonsIntegerChecker();

        validIntegers = new ArrayList<>(DATASET_SIZE);
        invalidIntegers = new ArrayList<>(DATASET_SIZE);
        largeValidIntegers = new ArrayList<>(DATASET_SIZE);

        for (int i = 0; i < DATASET_SIZE; i++) {
            validIntegers.add(String.valueOf(random.nextInt(1000000)));
            largeValidIntegers.add(String.valueOf(Integer.MAX_VALUE - random.nextInt(1000)));
            invalidIntegers.add(generateInvalidInteger());
        }
    }

    private String generateInvalidInteger() {
        int type = random.nextInt(8);
        return switch (type) {
            case 0 -> "abc" + random.nextInt(1000);
            case 1 -> random.nextInt(1000) + "xyz";
            case 2 -> random.nextInt(100) + "." + random.nextInt(100);
            case 3 -> " " + random.nextInt(1000);
            case 4 -> random.nextInt(1000) + " ";
            case 5 -> String.valueOf((long) Integer.MAX_VALUE + random.nextInt(1000000));
            case 6 -> random.nextInt(1000) + "e10";
            default -> "abc123def";
        };
    }

    private String getNextValid() {
        return validIntegers.get((index++) % DATASET_SIZE);
    }

    private String getNextInvalid() {
        return invalidIntegers.get((index++) % DATASET_SIZE);
    }

    private String getNextLarge() {
        return largeValidIntegers.get((index++) % DATASET_SIZE);
    }

    @Benchmark
    public void tryCatch_valid(Blackhole blackhole) {
        blackhole.consume(tryCatchChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void tryCatch_invalid(Blackhole blackhole) {
        blackhole.consume(tryCatchChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void tryCatch_large(Blackhole blackhole) {
        blackhole.consume(tryCatchChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void regex_valid(Blackhole blackhole) {
        blackhole.consume(regexChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void regex_invalid(Blackhole blackhole) {
        blackhole.consume(regexChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void regex_large(Blackhole blackhole) {
        blackhole.consume(regexChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void manualParsing_valid(Blackhole blackhole) {
        blackhole.consume(manualParsingChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void manualParsing_invalid(Blackhole blackhole) {
        blackhole.consume(manualParsingChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void manualParsing_large(Blackhole blackhole) {
        blackhole.consume(manualParsingChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void charArray_valid(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void charArray_invalid(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void charArray_large(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void stream_valid(Blackhole blackhole) {
        blackhole.consume(streamChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void stream_invalid(Blackhole blackhole) {
        blackhole.consume(streamChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void stream_large(Blackhole blackhole) {
        blackhole.consume(streamChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void optimizedTryCatch_valid(Blackhole blackhole) {
        blackhole.consume(optimizedTryCatchChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void optimizedTryCatch_invalid(Blackhole blackhole) {
        blackhole.consume(optimizedTryCatchChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void optimizedTryCatch_large(Blackhole blackhole) {
        blackhole.consume(optimizedTryCatchChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void scanner_valid(Blackhole blackhole) {
        blackhole.consume(scannerChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void scanner_invalid(Blackhole blackhole) {
        blackhole.consume(scannerChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void scanner_large(Blackhole blackhole) {
        blackhole.consume(scannerChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void matches_valid(Blackhole blackhole) {
        blackhole.consume(matchesChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void matches_invalid(Blackhole blackhole) {
        blackhole.consume(matchesChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void matches_large(Blackhole blackhole) {
        blackhole.consume(matchesChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void charAt_valid(Blackhole blackhole) {
        blackhole.consume(charAtChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void charAt_invalid(Blackhole blackhole) {
        blackhole.consume(charAtChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void charAt_large(Blackhole blackhole) {
        blackhole.consume(charAtChecker.isInteger(getNextLarge()));
    }

    @Benchmark
    public void apacheCommons_valid(Blackhole blackhole) {
        blackhole.consume(apacheCommonsChecker.isInteger(getNextValid()));
    }

    @Benchmark
    public void apacheCommons_invalid(Blackhole blackhole) {
        blackhole.consume(apacheCommonsChecker.isInteger(getNextInvalid()));
    }

    @Benchmark
    public void apacheCommons_large(Blackhole blackhole) {
        blackhole.consume(apacheCommonsChecker.isInteger(getNextLarge()));
    }

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder()
                .include(IntegerCheckerBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
