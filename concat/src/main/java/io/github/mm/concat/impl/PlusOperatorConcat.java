package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using the + operator inside a loop.
 *
 * Complexity: O(n²) - creates a new String object on every iteration
 * Use case: Never use in loops. Only for one-off, small concatenations.
 */
public class PlusOperatorConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        String result = "";
        for (String item : items) {
            result = result + item;
        }
        return result;
    }
}

