package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.util.List;
import java.util.stream.Collectors;

/**
 * String concatenation using Parallel Stream with Collectors.joining().
 * 
 * Complexity: O(n/k) where k = number of cores - but thread overhead and final merge 
 * cost dominate for small inputs
 * Use case: Only beneficial for very large datasets (10,000+ strings). 
 * Parallel overhead makes this slower than StringBuilder for typical inputs.
 */
public class ParallelStreamConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        return items.parallelStream().collect(Collectors.joining());
    }
}

