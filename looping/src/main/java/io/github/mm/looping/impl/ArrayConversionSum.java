package io.github.mm.looping.impl;

import io.github.mm.looping.SumStrategy;
import java.util.List;

public class ArrayConversionSum implements SumStrategy {

    @Override
    public long sumElements(List<Long> data) {
        long[] arr = data.stream().mapToLong(Long::longValue).toArray();
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
