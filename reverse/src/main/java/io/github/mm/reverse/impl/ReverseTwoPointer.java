package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;

public class ReverseTwoPointer implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        char[] c = input.toCharArray();
        for (int i = 0, j = c.length - 1; i < j; i++, j--) {
            char t = c[i];
            c[i] = c[j];
            c[j] = t;
        }
        return new String(c);
    }
}
