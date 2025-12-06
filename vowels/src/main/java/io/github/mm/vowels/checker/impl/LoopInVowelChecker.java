package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class LoopInVowelChecker implements VowelChecker {
    private static final String VOWELS = "aeiouAEIOU";

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        for (var c : text.toCharArray()) {
            if (VOWELS.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
}
