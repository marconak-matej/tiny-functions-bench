package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class IndexedForLoopSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long sum = 0;
        var n = data.size();
        for (int i = 0; i < n; i++) {
            sum += data.get(i);
        }
        return sum;
    }
}
