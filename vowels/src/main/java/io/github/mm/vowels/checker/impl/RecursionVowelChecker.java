package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class RecursionVowelChecker implements VowelChecker {
    private static final String VOWELS = "aeiouAEIOU";

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        return recursiveCheck(text);
    }

    private boolean recursiveCheck(String s) {
        // Base case: empty string
        if (s.isEmpty()) {
            return false;
        }

        return VOWELS.indexOf(s.charAt(0)) != -1 || recursiveCheck(s.substring(1));
    }
}
