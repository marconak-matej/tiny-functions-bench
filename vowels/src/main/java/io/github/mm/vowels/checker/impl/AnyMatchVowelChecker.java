package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class AnyMatchVowelChecker implements VowelChecker {
    private static final String VOWELS = "aeiouAEIOU";

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return text.chars().anyMatch(c -> VOWELS.indexOf(c) != -1);
    }
}
