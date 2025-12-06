package io.github.mm.vowels.benchmark;

import io.github.mm.vowels.checker.impl.*;
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
public class VowelCheckerBenchmark {
    private static final int STRING_LENGTH = 100;
    private static final Random random = new Random(42); // Fixed seed for reproducibility

    private String stringWithVowels;
    private String stringWithoutVowels;

    private LoopOrVowelChecker loopOrChecker;
    private LoopInVowelChecker loopInChecker;
    private RegexVowelChecker regexChecker;
    private AnyMatchContainsVowelChecker anyMatchContainsChecker;
    private BitSetVowelChecker bitSetChecker;
    private CharArrayVowelChecker charArrayChecker;
    private ByteMaskVowelChecker byteMaskChecker;
    private AnyMatchVowelChecker anyMatchChecker;
    private NestedForVowelChecker nestedForChecker;
    private RecursionVowelChecker recursionChecker;
    private RegexReplaceVowelChecker regexReplaceChecker;
    private ContainsVowelChecker containsChecker;
    private StringReplaceVowelChecker stringReplaceChecker;

    @Setup
    public void setup() {
        // Initialize all checkers
        loopOrChecker = new LoopOrVowelChecker();
        loopInChecker = new LoopInVowelChecker();
        regexChecker = new RegexVowelChecker();
        anyMatchContainsChecker = new AnyMatchContainsVowelChecker();
        bitSetChecker = new BitSetVowelChecker();
        charArrayChecker = new CharArrayVowelChecker();
        byteMaskChecker = new ByteMaskVowelChecker();
        anyMatchChecker = new AnyMatchVowelChecker();
        nestedForChecker = new NestedForVowelChecker();
        recursionChecker = new RecursionVowelChecker();
        regexReplaceChecker = new RegexReplaceVowelChecker();
        containsChecker = new ContainsVowelChecker();
        stringReplaceChecker = new StringReplaceVowelChecker();

        // Generate test strings
        stringWithVowels = generateString(true);
        stringWithoutVowels = generateString(false);
    }

    private String generateString(boolean includeVowels) {
        var sb = new StringBuilder(STRING_LENGTH);
        var chars = includeVowels
                ? "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                : "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";

        for (var i = 0; i < STRING_LENGTH; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Benchmark
    public void loopOr_withVowels(Blackhole blackhole) {
        blackhole.consume(loopOrChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void loopOr_withoutVowels(Blackhole blackhole) {
        blackhole.consume(loopOrChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void loopIn_withVowels(Blackhole blackhole) {
        blackhole.consume(loopInChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void loopIn_withoutVowels(Blackhole blackhole) {
        blackhole.consume(loopInChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void regex_withVowels(Blackhole blackhole) {
        blackhole.consume(regexChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void regex_withoutVowels(Blackhole blackhole) {
        blackhole.consume(regexChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void anyMatchContains_withVowels(Blackhole blackhole) {
        blackhole.consume(anyMatchContainsChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void anyMatchContains_withoutVowels(Blackhole blackhole) {
        blackhole.consume(anyMatchContainsChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void bitSet_withVowels(Blackhole blackhole) {
        blackhole.consume(bitSetChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void bitSet_withoutVowels(Blackhole blackhole) {
        blackhole.consume(bitSetChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void charArray_withVowels(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void charArray_withoutVowels(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void byteMask_withVowels(Blackhole blackhole) {
        blackhole.consume(byteMaskChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void byteMask_withoutVowels(Blackhole blackhole) {
        blackhole.consume(byteMaskChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void anyMatch_withVowels(Blackhole blackhole) {
        blackhole.consume(anyMatchChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void anyMatch_withoutVowels(Blackhole blackhole) {
        blackhole.consume(anyMatchChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void nestedFor_withVowels(Blackhole blackhole) {
        blackhole.consume(nestedForChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void nestedFor_withoutVowels(Blackhole blackhole) {
        blackhole.consume(nestedForChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void recursion_withVowels(Blackhole blackhole) {
        blackhole.consume(recursionChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void recursion_withoutVowels(Blackhole blackhole) {
        blackhole.consume(recursionChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void regexReplace_withVowels(Blackhole blackhole) {
        blackhole.consume(regexReplaceChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void regexReplace_withoutVowels(Blackhole blackhole) {
        blackhole.consume(regexReplaceChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void contains_withVowels(Blackhole blackhole) {
        blackhole.consume(containsChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void contains_withoutVowels(Blackhole blackhole) {
        blackhole.consume(containsChecker.hasVowels(stringWithoutVowels));
    }

    @Benchmark
    public void stringReplace_withVowels(Blackhole blackhole) {
        blackhole.consume(stringReplaceChecker.hasVowels(stringWithVowels));
    }

    @Benchmark
    public void stringReplace_withoutVowels(Blackhole blackhole) {
        blackhole.consume(stringReplaceChecker.hasVowels(stringWithoutVowels));
    }

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder()
                .include(VowelCheckerBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
