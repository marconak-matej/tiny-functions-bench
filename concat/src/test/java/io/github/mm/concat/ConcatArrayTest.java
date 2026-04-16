package io.github.mm.concat;

import static org.junit.jupiter.api.Assertions.*;

import io.github.mm.concat.impl.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConcatArrayTest {

    private static final List<ConcatArray> concatters = List.of(
            new PlusOperatorConcat(),
            new StringConcatMethodConcat(),
            new StringFormatConcat(),
            new StringBufferConcat(),
            new StringBuilderConcat(),
            new StringBuilderWithCapacityConcat(),
            new StringJoinConcat(),
            new CharArrayConcat(),
            new ByteBufferConcat(),
            new StreamConcat(),
            new ParallelStreamConcat());

    static Stream<Arguments> concatArrayProvider() {
        return concatters.stream().map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldHandleEmptyList(ConcatArray concat) {
        assertEquals("", concat.concat(Collections.emptyList()));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldHandleSingleItem(ConcatArray concat) {
        assertEquals("hello", concat.concat(List.of("hello")));
        assertEquals("", concat.concat(List.of("")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateMultipleItems(ConcatArray concat) {
        assertEquals("hello", concat.concat(List.of("h", "e", "l", "l", "o")));
        assertEquals("helloworld", concat.concat(List.of("hello", "world")));
        assertEquals("abcdefghij", concat.concat(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateWithEmptyStringsInList(ConcatArray concat) {
        assertEquals("hello", concat.concat(List.of("hello", "", "")));
        assertEquals("hello", concat.concat(List.of("", "hello", "")));
        assertEquals("hello", concat.concat(List.of("", "", "hello")));
        assertEquals("helloworld", concat.concat(List.of("hello", "", "world")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateWithSpecialCharacters(ConcatArray concat) {
        assertEquals("!@#$%^&*()", concat.concat(List.of("!", "@", "#", "$", "%", "^", "&", "*", "(", ")")));
        assertEquals("hello\nworld", concat.concat(List.of("hello\n", "world")));
        assertEquals("hello\tworld", concat.concat(List.of("hello\t", "world")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateWhitespace(ConcatArray concat) {
        assertEquals("hello world", concat.concat(List.of("hello", " ", "world")));
        assertEquals("   ", concat.concat(List.of(" ", " ", " ")));
        assertEquals("hello world ", concat.concat(List.of("hello", " ", "world", " ")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateLongString(ConcatArray concat) {
        String longString = "a".repeat(1000);
        assertEquals(longString, concat.concat(List.of(longString)));

        String part1 = "a".repeat(500);
        String part2 = "b".repeat(500);
        assertEquals(part1 + part2, concat.concat(List.of(part1, part2)));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateManyItems(ConcatArray concat) {
        StringBuilder expected = new StringBuilder();

        List<String> actualItems = new java.util.ArrayList<>();
        for (int i = 0; i < 100; i++) {
            actualItems.add("item");
            expected.append("item");
        }

        assertEquals(expected.toString(), concat.concat(actualItems));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldPreserveCaseSensitivity(ConcatArray concat) {
        assertEquals("HelloWORLD", concat.concat(List.of("Hello", "WORLD")));
        assertEquals("helloWorld", concat.concat(List.of("hello", "World")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateNumericStrings(ConcatArray concat) {
        assertEquals("123456789", concat.concat(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9")));
        assertEquals("0123456789", concat.concat(List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldConcatenateWithSingleCharacterItems(ConcatArray concat) {
        List<String> alphabet = List.of(
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z");
        assertEquals("abcdefghijklmnopqrstuvwxyz", concat.concat(alphabet));
    }

    @ParameterizedTest
    @MethodSource("concatArrayProvider")
    void shouldMaintainContentWithDuplicates(ConcatArray concat) {
        assertEquals("hellohelloadded", concat.concat(List.of("hello", "hello", "added")));
        assertEquals("aaaa", concat.concat(List.of("a", "a", "a", "a")));
    }
}

