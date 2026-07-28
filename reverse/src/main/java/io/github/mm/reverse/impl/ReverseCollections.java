package io.github.mm.reverse.impl;

import io.github.mm.reverse.ReverseStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseCollections implements ReverseStrategy {

    @Override
    public String reverse(String input) {
        if (input == null) return null;
        List<Character> characters = new ArrayList<>(input.length());
        for (char character : input.toCharArray()) {
            characters.add(character);
        }
        Collections.reverse(characters);
        StringBuilder result = new StringBuilder(input.length());
        for (char character : characters) {
            result.append(character);
        }
        return result.toString();
    }
}