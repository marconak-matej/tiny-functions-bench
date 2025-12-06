package io.github.mm.isinteger.checker;

import static org.junit.jupiter.api.Assertions.*;

import io.github.mm.isinteger.checker.impl.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IntegerCheckerTest {

    private static final List<IntegerChecker> checkers = List.of(
            new TryCatchIntegerChecker(),
            new RegexIntegerChecker(),
            new ManualParsingIntegerChecker(),
            new CharArrayIntegerChecker(),
            new StreamIntegerChecker(),
            new OptimizedTryCatchIntegerChecker(),
            new ScannerIntegerChecker(),
            new MatchesIntegerChecker(),
            new CharAtIntegerChecker(),
            new ApacheCommonsIntegerChecker());

    static Stream<Arguments> integerCheckerProvider() {
        return checkers.stream().map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("integerCheckerProvider")
    void shouldAcceptValidIntegers(IntegerChecker checker) {
        assertTrue(checker.isInteger("0"));
        assertTrue(checker.isInteger("1"));
        assertTrue(checker.isInteger("123"));
        assertTrue(checker.isInteger("2147483647")); // Integer.MAX_VALUE
        assertTrue(checker.isInteger("-2147483648")); // Integer.MIN_VALUE
        assertTrue(checker.isInteger("+123"));
        assertTrue(checker.isInteger("-456"));
        assertTrue(checker.isInteger("007")); // Leading zeros
    }

    @ParameterizedTest
    @MethodSource("integerCheckerProvider")
    void shouldRejectInvalidInputs(IntegerChecker checker) {
        assertFalse(checker.isInteger(null));
        assertFalse(checker.isInteger(""));
        assertFalse(checker.isInteger(" "));
        assertFalse(checker.isInteger("   "));
        assertFalse(checker.isInteger("abc"));
        assertFalse(checker.isInteger("12.34"));
        assertFalse(checker.isInteger("12a34"));
        assertFalse(checker.isInteger("1e10"));
        assertFalse(checker.isInteger(" 123"));
        assertFalse(checker.isInteger("123 "));
        assertFalse(checker.isInteger("12 34"));
        assertFalse(checker.isInteger("+"));
        assertFalse(checker.isInteger("-"));
        assertFalse(checker.isInteger("--123"));
        assertFalse(checker.isInteger("++123"));
        assertFalse(checker.isInteger("+-123"));
    }

    @ParameterizedTest
    @MethodSource("integerCheckerProvider")
    void shouldRejectOutOfRangeIntegers(IntegerChecker checker) {
        assertFalse(checker.isInteger("2147483648")); // Integer.MAX_VALUE + 1
        assertFalse(checker.isInteger("-2147483649")); // Integer.MIN_VALUE - 1
        assertFalse(checker.isInteger("9999999999")); // Way too large
        assertFalse(checker.isInteger("-9999999999")); // Way too small
    }

    @ParameterizedTest
    @MethodSource("integerCheckerProvider")
    void shouldHandleEdgeCases(IntegerChecker checker) {
        assertTrue(checker.isInteger("0"));
        assertTrue(checker.isInteger("-0"));
        assertTrue(checker.isInteger("+0"));
        assertTrue(checker.isInteger("00000"));
        assertTrue(checker.isInteger("000123"));
    }

    @ParameterizedTest
    @MethodSource("integerCheckerProvider")
    void shouldHandleBoundaryValues(IntegerChecker checker) {
        assertTrue(checker.isInteger(String.valueOf(Integer.MAX_VALUE)));
        assertTrue(checker.isInteger(String.valueOf(Integer.MIN_VALUE)));
        assertTrue(checker.isInteger(String.valueOf(Integer.MAX_VALUE - 1)));
        assertTrue(checker.isInteger(String.valueOf(Integer.MIN_VALUE + 1)));

        assertFalse(checker.isInteger(String.valueOf((long) Integer.MAX_VALUE + 1)));
        assertFalse(checker.isInteger(String.valueOf((long) Integer.MIN_VALUE - 1)));
    }

    @ParameterizedTest
    @MethodSource("integerCheckerProvider")
    void shouldHandleSignedNumbers(IntegerChecker checker) {
        assertTrue(checker.isInteger("+1"));
        assertTrue(checker.isInteger("+999"));
        assertTrue(checker.isInteger("-1"));
        assertTrue(checker.isInteger("-999"));

        assertFalse(checker.isInteger("1-"));
        assertFalse(checker.isInteger("1+"));
        assertFalse(checker.isInteger("12-34"));
        assertFalse(checker.isInteger("12+34"));
    }
}
