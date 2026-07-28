package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;

public class ReverseLoop implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = input.length() - 1; i >= 0; i--) {
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }
}
