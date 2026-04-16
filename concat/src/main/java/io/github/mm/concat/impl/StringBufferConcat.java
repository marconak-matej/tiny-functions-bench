package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using the legacy StringBuffer (thread-safe, synchronized).
 * 
 * Complexity: O(n) - mutable internal buffer, but synchronization adds overhead per append()
 * Use case: Only when the same buffer is genuinely shared and mutated across multiple 
 * threads simultaneously. Prefer StringBuilder in all single-threaded contexts.
 */
public class StringBufferConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        StringBuffer sb = new StringBuffer();
        for (String item : items) {
            sb.append(item);
        }
        return sb.toString();
    }
}

