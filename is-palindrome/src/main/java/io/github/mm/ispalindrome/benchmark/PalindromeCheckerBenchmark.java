package io.github.mm.ispalindrome.benchmark;

import io.github.mm.ispalindrome.checker.impl.*;
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
public class PalindromeCheckerBenchmark {
    private static final Random random = new Random(42);
    private static final int DATASET_SIZE = 10000;

    private List<String> shortPalindromes;
    private List<String> shortNonPalindromes;
    private List<String> mediumPalindromes;
    private List<String> mediumNonPalindromes;
    private List<String> longPalindromes;
    private List<String> longNonPalindromes;
    private List<String> phrasesWithSpaces;
    private int index = 0;

    private TwoPointerPalindromeChecker twoPointerChecker;
    private StringBuilderPalindromeChecker stringBuilderChecker;
    private RegexPalindromeChecker regexChecker;
    private RecursivePalindromeChecker recursiveChecker;
    private HalfStringPalindromeChecker halfStringChecker;
    private StreamPalindromeChecker streamChecker;
    private CharArrayPalindromeChecker charArrayChecker;

    @Setup
    public void setup() {
        twoPointerChecker = new TwoPointerPalindromeChecker();
        stringBuilderChecker = new StringBuilderPalindromeChecker();
        regexChecker = new RegexPalindromeChecker();
        recursiveChecker = new RecursivePalindromeChecker();
        halfStringChecker = new HalfStringPalindromeChecker();
        streamChecker = new StreamPalindromeChecker();
        charArrayChecker = new CharArrayPalindromeChecker();

        shortPalindromes = new ArrayList<>(DATASET_SIZE);
        shortNonPalindromes = new ArrayList<>(DATASET_SIZE);
        mediumPalindromes = new ArrayList<>(DATASET_SIZE);
        mediumNonPalindromes = new ArrayList<>(DATASET_SIZE);
        longPalindromes = new ArrayList<>(DATASET_SIZE);
        longNonPalindromes = new ArrayList<>(DATASET_SIZE);
        phrasesWithSpaces = new ArrayList<>(DATASET_SIZE);

        for (int i = 0; i < DATASET_SIZE; i++) {
            shortPalindromes.add(generatePalindrome(5));
            shortNonPalindromes.add(generateNonPalindrome(5));
            mediumPalindromes.add(generatePalindrome(20));
            mediumNonPalindromes.add(generateNonPalindrome(20));
            longPalindromes.add(generatePalindrome(100));
            longNonPalindromes.add(generateNonPalindrome(100));
            phrasesWithSpaces.add(generatePhraseWithSpaces());
        }
    }

    private String generatePalindrome(int length) {
        StringBuilder sb = new StringBuilder();
        int halfLength = length / 2;
        for (int i = 0; i < halfLength; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        if (length % 2 == 1) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        String half = sb.toString();
        String firstHalf = half.substring(0, halfLength);
        return firstHalf + (length % 2 == 1 ? half.charAt(halfLength) : "")
                + new StringBuilder(firstHalf).reverse();
    }

    private String generateNonPalindrome(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        String result = sb.toString();
        if (result.equals(new StringBuilder(result).reverse().toString())) {
            sb.setCharAt(0, (char) ('a' + (sb.charAt(0) - 'a' + 1) % 26));
            result = sb.toString();
        }
        return result;
    }

    private String generatePhraseWithSpaces() {
        String[] phrases = {
            "A man a plan a canal Panama",
            "race car",
            "Was it a car or a cat I saw",
            "Madam Im Adam",
            "hello world",
            "not a palindrome at all"
        };
        return phrases[random.nextInt(phrases.length)];
    }

    private String getNext(List<String> list) {
        return list.get((index++) % DATASET_SIZE);
    }

    @Benchmark
    public void twoPointer_shortPalindrome(Blackhole blackhole) {
        blackhole.consume(twoPointerChecker.isPalindrome(getNext(shortPalindromes)));
    }

    @Benchmark
    public void twoPointer_shortNonPalindrome(Blackhole blackhole) {
        blackhole.consume(twoPointerChecker.isPalindrome(getNext(shortNonPalindromes)));
    }

    @Benchmark
    public void twoPointer_mediumPalindrome(Blackhole blackhole) {
        blackhole.consume(twoPointerChecker.isPalindrome(getNext(mediumPalindromes)));
    }

    @Benchmark
    public void twoPointer_mediumNonPalindrome(Blackhole blackhole) {
        blackhole.consume(twoPointerChecker.isPalindrome(getNext(mediumNonPalindromes)));
    }

    @Benchmark
    public void twoPointer_longPalindrome(Blackhole blackhole) {
        blackhole.consume(twoPointerChecker.isPalindrome(getNext(longPalindromes)));
    }

    @Benchmark
    public void twoPointer_longNonPalindrome(Blackhole blackhole) {
        blackhole.consume(twoPointerChecker.isPalindrome(getNext(longNonPalindromes)));
    }

    @Benchmark
    public void stringBuilder_shortPalindrome(Blackhole blackhole) {
        blackhole.consume(stringBuilderChecker.isPalindrome(getNext(shortPalindromes)));
    }

    @Benchmark
    public void stringBuilder_shortNonPalindrome(Blackhole blackhole) {
        blackhole.consume(stringBuilderChecker.isPalindrome(getNext(shortNonPalindromes)));
    }

    @Benchmark
    public void stringBuilder_mediumPalindrome(Blackhole blackhole) {
        blackhole.consume(stringBuilderChecker.isPalindrome(getNext(mediumPalindromes)));
    }

    @Benchmark
    public void stringBuilder_mediumNonPalindrome(Blackhole blackhole) {
        blackhole.consume(stringBuilderChecker.isPalindrome(getNext(mediumNonPalindromes)));
    }

    @Benchmark
    public void stringBuilder_longPalindrome(Blackhole blackhole) {
        blackhole.consume(stringBuilderChecker.isPalindrome(getNext(longPalindromes)));
    }

    @Benchmark
    public void stringBuilder_longNonPalindrome(Blackhole blackhole) {
        blackhole.consume(stringBuilderChecker.isPalindrome(getNext(longNonPalindromes)));
    }

    @Benchmark
    public void regex_phrases(Blackhole blackhole) {
        blackhole.consume(regexChecker.isPalindrome(getNext(phrasesWithSpaces)));
    }

    @Benchmark
    public void recursive_shortPalindrome(Blackhole blackhole) {
        blackhole.consume(recursiveChecker.isPalindrome(getNext(shortPalindromes)));
    }

    @Benchmark
    public void recursive_shortNonPalindrome(Blackhole blackhole) {
        blackhole.consume(recursiveChecker.isPalindrome(getNext(shortNonPalindromes)));
    }

    @Benchmark
    public void recursive_mediumPalindrome(Blackhole blackhole) {
        blackhole.consume(recursiveChecker.isPalindrome(getNext(mediumPalindromes)));
    }

    @Benchmark
    public void recursive_mediumNonPalindrome(Blackhole blackhole) {
        blackhole.consume(recursiveChecker.isPalindrome(getNext(mediumNonPalindromes)));
    }

    @Benchmark
    public void halfString_shortPalindrome(Blackhole blackhole) {
        blackhole.consume(halfStringChecker.isPalindrome(getNext(shortPalindromes)));
    }

    @Benchmark
    public void halfString_shortNonPalindrome(Blackhole blackhole) {
        blackhole.consume(halfStringChecker.isPalindrome(getNext(shortNonPalindromes)));
    }

    @Benchmark
    public void halfString_mediumPalindrome(Blackhole blackhole) {
        blackhole.consume(halfStringChecker.isPalindrome(getNext(mediumPalindromes)));
    }

    @Benchmark
    public void halfString_mediumNonPalindrome(Blackhole blackhole) {
        blackhole.consume(halfStringChecker.isPalindrome(getNext(mediumNonPalindromes)));
    }

    @Benchmark
    public void halfString_longPalindrome(Blackhole blackhole) {
        blackhole.consume(halfStringChecker.isPalindrome(getNext(longPalindromes)));
    }

    @Benchmark
    public void halfString_longNonPalindrome(Blackhole blackhole) {
        blackhole.consume(halfStringChecker.isPalindrome(getNext(longNonPalindromes)));
    }

    @Benchmark
    public void stream_shortPalindrome(Blackhole blackhole) {
        blackhole.consume(streamChecker.isPalindrome(getNext(shortPalindromes)));
    }

    @Benchmark
    public void stream_shortNonPalindrome(Blackhole blackhole) {
        blackhole.consume(streamChecker.isPalindrome(getNext(shortNonPalindromes)));
    }

    @Benchmark
    public void stream_mediumPalindrome(Blackhole blackhole) {
        blackhole.consume(streamChecker.isPalindrome(getNext(mediumPalindromes)));
    }

    @Benchmark
    public void stream_mediumNonPalindrome(Blackhole blackhole) {
        blackhole.consume(streamChecker.isPalindrome(getNext(mediumNonPalindromes)));
    }

    @Benchmark
    public void stream_longPalindrome(Blackhole blackhole) {
        blackhole.consume(streamChecker.isPalindrome(getNext(longPalindromes)));
    }

    @Benchmark
    public void stream_longNonPalindrome(Blackhole blackhole) {
        blackhole.consume(streamChecker.isPalindrome(getNext(longNonPalindromes)));
    }

    @Benchmark
    public void charArray_shortPalindrome(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isPalindrome(getNext(shortPalindromes)));
    }

    @Benchmark
    public void charArray_shortNonPalindrome(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isPalindrome(getNext(shortNonPalindromes)));
    }

    @Benchmark
    public void charArray_mediumPalindrome(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isPalindrome(getNext(mediumPalindromes)));
    }

    @Benchmark
    public void charArray_mediumNonPalindrome(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isPalindrome(getNext(mediumNonPalindromes)));
    }

    @Benchmark
    public void charArray_longPalindrome(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isPalindrome(getNext(longPalindromes)));
    }

    @Benchmark
    public void charArray_longNonPalindrome(Blackhole blackhole) {
        blackhole.consume(charArrayChecker.isPalindrome(getNext(longNonPalindromes)));
    }

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder()
                .include(PalindromeCheckerBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
