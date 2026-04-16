package io.github.mm.concat;

import java.util.List;

/**
 * Common interface for all string concatenation implementations.
 * Each implementation concatenates all strings in the provided list
 * into a single result string, with no delimiter.
 */
public interface ConcatArray {

    /**
     * Concatenates all strings in the list into a single string.
     *
     * @param items the list of strings to concatenate; must not be null
     * @return the concatenated result; empty string if list is empty
     */
    String concat(List<String> items);
}

