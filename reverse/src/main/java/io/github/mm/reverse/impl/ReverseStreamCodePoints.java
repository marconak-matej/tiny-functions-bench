package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;

import java.util.stream.Collectors;

public class ReverseStreamCodePoints implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        return input.codePoints()
                .mapToObj(Character::toString)
                .reduce("", (a, b) -> b + a);
    }
}