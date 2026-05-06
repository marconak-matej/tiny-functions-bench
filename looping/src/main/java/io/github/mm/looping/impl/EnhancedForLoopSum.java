package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class EnhancedForLoopSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long sum = 0;
        for (Long n : data) {
            sum += n;
        }
        return sum;
    }
}
