package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class IterableForEachSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long[] sum = {0};
        data.forEach(n -> sum[0] += n);
        return sum[0];
    }
}
