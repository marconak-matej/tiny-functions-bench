package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class StreamReduceSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        return data.stream().reduce(0L, Long::sum);
    }
}
