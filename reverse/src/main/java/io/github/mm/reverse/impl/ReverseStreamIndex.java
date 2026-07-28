package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;
import java.util.stream.IntStream;

public class ReverseStreamIndex implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        return IntStream.range(0, input.length())
                .mapToObj(i -> String.valueOf(input.charAt(input.length() - 1 - i)))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
