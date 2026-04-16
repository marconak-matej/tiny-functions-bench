package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using StringBuilder with pre-allocated capacity.
 * 
 * Complexity: O(n) with zero internal reallocations - no char[] doubling at all
 * Use case: When the input size is known (or estimable) before the loop. 
 * Best-in-class among StringBuilder-based approaches.
 */
public class StringBuilderWithCapacityConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        int totalLength = 0;
        for (String item : items) {
            totalLength += item.length();
        }

        StringBuilder sb = new StringBuilder(totalLength);
        for (String item : items) {
            sb.append(item);
        }
        return sb.toString();
    }
}

