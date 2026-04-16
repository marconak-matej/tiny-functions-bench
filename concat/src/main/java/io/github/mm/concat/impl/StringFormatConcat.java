package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;

/**
 * String concatenation using String.format() inside a loop.
 * 
 * Complexity: O(n²) + format-string parsing overhead on every call
 * Use case: Never use for building strings in a loop. String.format() is intended for 
 * one-shot formatting of a final value, not for accumulation.
 */
public class StringFormatConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        String result = "";
        for (String item : items) {
            result = String.format("%s%s", result, item);
        }
        return result;
    }
}

