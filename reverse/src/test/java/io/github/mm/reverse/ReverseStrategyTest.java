package io.github.mm.reverse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.mm.reverse.impl.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReverseStrategyTest {

    private static final List<ReverseStrategy> strategies = List.of(
            new ReverseStringBuilder(),
            new ReverseTwoPointer(),
            new ReverseLoop(),
            new ReverseRecursive(),
            new ReverseStreamCodePoints(),
            new ReverseStreamIndex(),
            new ReverseStack(),
            new ReverseCollections());

    @ParameterizedTest(name = "{0} - {1}")
    @MethodSource("provideTestCases")
    void testReverseString(ReverseStrategy strategy, String testName, String input, String expected) {
        if (input == null) {
            assertNull(strategy.reverse(null), "Failed: " + strategy.getClass().getSimpleName() + " - " + testName);
            return;
        }
        assertEquals(
                expected,
                strategy.reverse(input),
                "Failed: " + strategy.getClass().getSimpleName() + " - " + testName);
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                        Arguments.of("empty string", "", ""),
                        Arguments.of("single character", "a", "a"),
                        Arguments.of("short string", "hello", "olleh"),
                        Arguments.of("medium string", "The quick brown fox", "xof nworb kciuq ehT"),
                        Arguments.of("palindrome", "racecar", "racecar"),
                        Arguments.of("unicode", "héllo", "olléh"),
                        Arguments.of("null input", null, null))
                .flatMap(args -> {
                    String testName = (String) args.get()[0];
                    String input = (String) args.get()[1];
                    String expected = (String) args.get()[2];
                    return strategies.stream().map(strategy -> Arguments.of(strategy, testName, input, expected));
                });
    }
}
