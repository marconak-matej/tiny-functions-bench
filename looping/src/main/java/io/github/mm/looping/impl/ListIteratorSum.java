package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class ListIteratorSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long sum = 0;
        var it = data.listIterator();
        while (it.hasNext()) {
            sum += it.next();
        }
        return sum;
    }
}
