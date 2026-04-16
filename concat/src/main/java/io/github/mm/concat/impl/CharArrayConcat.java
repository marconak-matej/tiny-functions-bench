package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using manual char[] write with getChars().
 * 
 * Complexity: O(n) - zero object allocation during the loop; single final String construction
 * Use case: When profiling confirms StringBuilder is a bottleneck due to its own allocation. 
 * Verbose, error-prone, rarely necessary - but the fastest pure-Java approach.
 */
public class CharArrayConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        int totalLength = 0;
        for (String item : items) {
            totalLength += item.length();
        }

        char[] buffer = new char[totalLength];
        int position = 0;
        for (String item : items) {
            int len = item.length();
            item.getChars(0, len, buffer, position);
            position += len;
        }

        return new String(buffer);
    }
}

