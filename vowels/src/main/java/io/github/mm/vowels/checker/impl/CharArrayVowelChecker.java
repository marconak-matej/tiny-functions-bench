package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class CharArrayVowelChecker implements VowelChecker {
    private static final boolean[] VOWELS = new boolean[128];

    static {
        for (var c : new char[] {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'}) {
            VOWELS[c] = true;
        }
    }

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        var length = text.length();
        for (var i = 0; i < length; i++) {
            var c = text.charAt(i);
            if (c < 128 && VOWELS[c]) {
                return true;
            }
        }
        return false;
    }
}
