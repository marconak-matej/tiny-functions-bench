package io.github.mm.looping;

import java.util.List;

/**
 * Common interface for all looping/iteration strategies.
 * Each implementation sums all elements in the provided list
 * using a different iteration technique.
 */
public interface SumStrategy {

    /**
     * Sums all elements in the given list.
     *
     * @param data a non-null List of Long values (elements are assumed non-null)
     * @return the sum of all elements as a long
     */
    long sumElements(List<Long> data);
}
