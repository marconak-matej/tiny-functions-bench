package io.github.mm.vowels.checker;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.mm.vowels.checker.impl.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class VowelCheckerTest {
    private static final int STRING_LENGTH = 100;
    private static final int TEST_STRING_COUNT = 10000;
    private static final String VOWELS = "aeiouAEIOU";
    private static final String NON_VOWELS = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
    private static List<String> testStringsWithVowels;
    private static List<String> testStringsWithoutVowels;
    private static final Random random = new Random();

    private static final List<VowelChecker> checkers = List.of(
            new LoopOrVowelChecker(),
            new LoopInVowelChecker(),
            new RegexVowelChecker(),
            new AnyMatchContainsVowelChecker(),
            new BitSetVowelChecker(),
            new CharArrayVowelChecker(),
            new ByteMaskVowelChecker(),
            new AnyMatchVowelChecker(),
            new NestedForVowelChecker(),
            new RecursionVowelChecker(),
            new RegexReplaceVowelChecker(),
            new ContainsVowelChecker(),
            new StringReplaceVowelChecker());

    @BeforeAll
    static void setUp() {
        // Generate test strings with vowels
        testStringsWithVowels = Stream.generate(() -> {
                    StringBuilder sb = new StringBuilder();
                    // Generate string with consonants
                    for (var i = 0; i < STRING_LENGTH - 1; i++) {
                        sb.append(NON_VOWELS.charAt(random.nextInt(NON_VOWELS.length())));
                    }
                    // Insert a random vowel at a random position
                    var insertPos = random.nextInt(STRING_LENGTH);
                    sb.insert(insertPos, VOWELS.charAt(random.nextInt(VOWELS.length())));
                    return sb.toString();
                })
                .limit(TEST_STRING_COUNT)
                .toList();

        // Generate test strings without vowels
        testStringsWithoutVowels = Stream.generate(() -> {
                    var sb = new StringBuilder();
                    for (var i = 0; i < STRING_LENGTH - 1; i++) {
                        sb.append(NON_VOWELS.charAt(random.nextInt(NON_VOWELS.length())));
                    }
                    return sb.toString();
                })
                .limit(TEST_STRING_COUNT)
                .toList();
    }

    static Stream<Arguments> vowelCheckerProvider() {
        return checkers.stream().map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldDetectVowels(VowelChecker checker) {
        for (var str : testStringsWithVowels) {
            assertTrue(
                    checker.hasVowels(str),
                    String.format(
                            "%s failed to detect vowels in: %s",
                            checker.getClass().getSimpleName(), str));
        }
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldNotDetectVowelsInConsonantOnlyStrings(VowelChecker checker) {
        for (var str : testStringsWithoutVowels) {
            assertFalse(
                    checker.hasVowels(str),
                    String.format(
                            "%s incorrectly detected vowels in: %s",
                            checker.getClass().getSimpleName(), str));
        }
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldHandleEmptyString(VowelChecker checker) {
        assertFalse(checker.hasVowels(""));
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldHandleSingleVowels(VowelChecker checker) {
        for (var vowel : VOWELS.toCharArray()) {
            assertTrue(checker.hasVowels(String.valueOf(vowel)));
        }
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldHandleSingleConsonants(VowelChecker checker) {
        for (var consonant : NON_VOWELS.toCharArray()) {
            assertFalse(checker.hasVowels(String.valueOf(consonant)));
        }
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldHandleNullInput(VowelChecker checker) {
        assertFalse(
                checker.hasVowels(null),
                String.format(
                        "%s failed to handle null input correctly",
                        checker.getClass().getSimpleName()));
    }

    @ParameterizedTest
    @MethodSource("vowelCheckerProvider")
    void shouldHandleVeryLargeString(VowelChecker checker) {
        // Create a 1MB string without vowels, then add one vowel
        var sb = new StringBuilder(1024 * 1024);
        sb.append("x".repeat(1024 * 1024 - 1));
        var noVowels = sb.toString();
        var withVowel = sb.append('a').toString();

        try {
            assertFalse(
                    checker.hasVowels(noVowels),
                    String.format(
                            "%s incorrectly detected vowels in large consonant-only string",
                            checker.getClass().getSimpleName()));
            assertTrue(
                    checker.hasVowels(withVowel),
                    String.format(
                            "%s failed to detect vowel in large string",
                            checker.getClass().getSimpleName()));
        } catch (StackOverflowError | OutOfMemoryError e) {
            assertInstanceOf(RecursionVowelChecker.class, checker);
        }
    }
}
