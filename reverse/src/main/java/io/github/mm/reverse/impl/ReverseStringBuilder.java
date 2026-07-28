package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;

public class ReverseStringBuilder implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        return new StringBuilder(input).reverse().toString();
    }
}