package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class ExplicitIteratorSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long sum = 0;
        var it = data.iterator();
        while (it.hasNext()) {
            sum += it.next();
        }
        return sum;
    }
}
