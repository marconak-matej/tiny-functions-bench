package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using String.join() (backed by StringJoiner and StringBuilder).
 * 
 * Complexity: O(n) - internally equivalent to StringBuilder, but more readable and idiomatic
 * Use case: Best when you need delimiter, prefix, or suffix logic, or when working with streams.
 * The most idiomatic Java 8+ approach.
 */
public class StringJoinConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        // String.join with empty delimiter concatenates all items
        return String.join("", items);
    }
}

