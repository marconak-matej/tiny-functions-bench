package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;
import java.util.stream.Collectors;

/**
 * String concatenation using Stream with Collectors.joining().
 *
 * Complexity: O(n) - uses internal StringJoiner backed by StringBuilder
 * Use case: Idiomatic when working with streams, especially with transformations.
 * Slightly more overhead than direct StringBuilder but cleaner code.
 */
public class StreamConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        return items.stream().collect(Collectors.joining());
    }
}

