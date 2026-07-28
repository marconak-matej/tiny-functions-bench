package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;

public class ReverseRecursive implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        return reverse(input.substring(1)) + input.charAt(0);
    }
}