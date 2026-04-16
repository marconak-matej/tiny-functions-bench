package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using String.concat() method inside a loop.
 *
 * Complexity: O(n²) - allocates a new String on every call, same as +
 * Use case: Avoid in loops. Marginally more explicit than + but no performance benefit.
 */
public class StringConcatMethodConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        String result = "";
        for (String item : items) {
            result = result.concat(item);
        }
        return result;
    }
}

