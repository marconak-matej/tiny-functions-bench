package io.github.mm.ispalindrome.checker;

import static org.junit.jupiter.api.Assertions.*;

import io.github.mm.ispalindrome.checker.impl.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalindromeCheckerTest {

    private static final List<PalindromeChecker> checkers = List.of(
            new TwoPointerPalindromeChecker(),
            new StringBuilderPalindromeChecker(),
            new RecursivePalindromeChecker(),
            new HalfStringPalindromeChecker(),
            new StreamPalindromeChecker(),
            new CharArrayPalindromeChecker());

    private static final List<PalindromeChecker> regexCheckers =
            List.of(new RegexPalindromeChecker());

    static Stream<Arguments> palindromeCheckerProvider() {
        return checkers.stream().map(Arguments::of);
    }

    static Stream<Arguments> regexPalindromeCheckerProvider() {
        return regexCheckers.stream().map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldAcceptValidPalindromes(PalindromeChecker checker) {
        assertTrue(checker.isPalindrome("a"));
        assertTrue(checker.isPalindrome("aa"));
        assertTrue(checker.isPalindrome("aba"));
        assertTrue(checker.isPalindrome("abba"));
        assertTrue(checker.isPalindrome("racecar"));
        assertTrue(checker.isPalindrome("noon"));
        assertTrue(checker.isPalindrome("level"));
        assertTrue(checker.isPalindrome("madam"));
        assertTrue(checker.isPalindrome("12321"));
        assertTrue(checker.isPalindrome("A"));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldRejectInvalidPalindromes(PalindromeChecker checker) {
        assertFalse(checker.isPalindrome("ab"));
        assertFalse(checker.isPalindrome("abc"));
        assertFalse(checker.isPalindrome("abcd"));
        assertFalse(checker.isPalindrome("hello"));
        assertFalse(checker.isPalindrome("world"));
        assertFalse(checker.isPalindrome("12345"));
        assertFalse(checker.isPalindrome("abcba1"));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldRejectNullAndEmpty(PalindromeChecker checker) {
        assertFalse(checker.isPalindrome(null));
        assertFalse(checker.isPalindrome(""));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldHandleLongPalindromes(PalindromeChecker checker) {
        assertTrue(checker.isPalindrome("abcdefghijklmnopqponmlkjihgfedcba"));
        assertFalse(checker.isPalindrome("abcdefghijklmnopqponmlkjihgfedcbx"));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldBeCaseSensitive(PalindromeChecker checker) {
        assertFalse(checker.isPalindrome("Aa"));
        assertFalse(checker.isPalindrome("Aba"));
        assertTrue(checker.isPalindrome("ABA"));
        assertTrue(checker.isPalindrome("aba"));
        assertTrue(checker.isPalindrome("AbA"));
        assertFalse(checker.isPalindrome("ABa"));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldNotIgnoreWhitespace(PalindromeChecker checker) {
        assertTrue(checker.isPalindrome("a b a"));
        assertTrue(checker.isPalindrome(" aa "));
        assertTrue(checker.isPalindrome("a a"));
        assertFalse(checker.isPalindrome(" aa"));
        assertFalse(checker.isPalindrome("aa "));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldHandleSpecialCharacters(PalindromeChecker checker) {
        assertTrue(checker.isPalindrome("!@#@!"));
        assertTrue(checker.isPalindrome("a!b!a"));
        assertFalse(checker.isPalindrome("a!b@a"));
    }

    @ParameterizedTest
    @MethodSource("palindromeCheckerProvider")
    void shouldHandleRepeatingCharacters(PalindromeChecker checker) {
        assertTrue(checker.isPalindrome("aaaa"));
        assertTrue(checker.isPalindrome("11111"));
        assertTrue(checker.isPalindrome("xxxxx"));
    }

    @ParameterizedTest
    @MethodSource("regexPalindromeCheckerProvider")
    void regexShouldHandlePhrasesWithWhitespaceAndPunctuation(PalindromeChecker checker) {
        assertTrue(checker.isPalindrome("A man a plan a canal Panama"));
        assertTrue(checker.isPalindrome("race car"));
        assertTrue(checker.isPalindrome("Was it a car or a cat I saw?"));
        assertTrue(checker.isPalindrome("Madam, I'm Adam"));
        assertTrue(checker.isPalindrome("No 'x' in Nixon"));
    }

    @ParameterizedTest
    @MethodSource("regexPalindromeCheckerProvider")
    void regexShouldRejectNonPalindromePhrases(PalindromeChecker checker) {
        assertFalse(checker.isPalindrome("hello world"));
        assertFalse(checker.isPalindrome("not a palindrome"));
    }

    @ParameterizedTest
    @MethodSource("regexPalindromeCheckerProvider")
    void regexShouldRejectEmptyAfterCleaning(PalindromeChecker checker) {
        assertFalse(checker.isPalindrome("!!!"));
        assertFalse(checker.isPalindrome("   "));
        assertFalse(checker.isPalindrome("@#$%"));
    }
}
