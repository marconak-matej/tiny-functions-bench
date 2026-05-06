package io.github.mm.looping;

import io.github.mm.looping.impl.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoopingBenchmarkTest {

    private static final List<SumStrategy> strategies = List.of(
        new IndexedForLoopSum(),
        new EnhancedForLoopSum(),
        new IterableForEachSum(),
        new StreamForEachSum(),
        new StreamReduceSum(),
        new ParallelStreamSum(),
        new ExplicitIteratorSum(),
        new ListIteratorSum(),
        new ArrayConversionSum()
    );

    @ParameterizedTest(name = "{0} - {1}")
    @MethodSource("provideTestCases")
    void testSumElements(SumStrategy strategy, String testName, List<Long> data, long expected) {
        assertEquals(expected, strategy.sumElements(data), 
            "Failed: " + strategy.getClass().getSimpleName() + " - " + testName);
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("Empty list", new ArrayList<Long>(), 0L),
                Arguments.of("Single element (42)", listOf(42L), 42L),
                Arguments.of("Multiple elements (1-5)", listOf(1L, 2L, 3L, 4L, 5L), 15L),
                Arguments.of("ArrayList 1-100", rangeList(new ArrayList<>()), 5050L),
                Arguments.of("LinkedList 1-100", rangeList(new LinkedList<>()), 5050L),
                Arguments.of("Negative numbers", listOf(10L, -5L, 3L, -8L), 0L),
                Arguments.of("Large numbers", listOf(Long.MAX_VALUE / 2, Long.MAX_VALUE / 2), Long.MAX_VALUE - 1)
            )
            .flatMap(args -> {
                String testName = (String) args.get()[0];
                @SuppressWarnings("unchecked") List<Long> data = (List<Long>) args.get()[1];
                long expected = (long) args.get()[2];
                
                return strategies.stream()
                    .map(strategy -> Arguments.of(strategy, testName, new ArrayList<>(data), expected));
            });
    }

    private static List<Long> listOf(Long... values) {
        return new ArrayList<>(Arrays.asList(values));
    }

    private static <T extends List<Long>> T rangeList(T list) {
        for (long i = 1; i <= (long) 100; i++) {
            list.add(i);
        }
        return list;
    }
}
