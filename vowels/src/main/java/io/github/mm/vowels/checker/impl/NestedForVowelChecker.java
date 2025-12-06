package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class NestedForVowelChecker implements VowelChecker {
    private static final String VOWELS = "aeiouAEIOU";

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        for (var c : text.toCharArray()) {
            for (var v : VOWELS.toCharArray()) {
                if (c == v) {
                    return true;
                }
            }
        }
        return false;
    }
}
