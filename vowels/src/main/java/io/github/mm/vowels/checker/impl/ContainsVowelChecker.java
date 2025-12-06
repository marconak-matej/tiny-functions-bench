package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class ContainsVowelChecker implements VowelChecker {
    private static final String VOWELS = "aeiouAEIOU";

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        for (var vowel : VOWELS.toCharArray()) {
            if (text.contains(String.valueOf(vowel))) {
                return true;
            }
        }
        return false;
    }
}
