package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class StreamForEachSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long[] sum = {0};
        data.stream().forEach(n -> sum[0] += n);
        return sum[0];
    }
}
