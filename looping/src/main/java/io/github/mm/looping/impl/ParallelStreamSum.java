package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class ParallelStreamSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        return data.parallelStream().mapToLong(Long::longValue).sum();
    }
}
