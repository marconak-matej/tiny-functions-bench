package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using StringBuilder (standard, non-synchronized).
 * 
 * Complexity: O(n) amortized - internal char[] doubles when capacity is exceeded
 * Use case: The default choice for any string concatenation in a loop. 
 * Preferred over StringBuffer in all single-threaded scenarios.
 */
public class StringBuilderConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(item);
        }
        return sb.toString();
    }
}

