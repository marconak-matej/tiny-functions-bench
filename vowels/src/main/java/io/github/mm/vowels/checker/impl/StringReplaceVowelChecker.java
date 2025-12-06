package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class StringReplaceVowelChecker implements VowelChecker {
    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        var current = text;
        var originalLength = text.length();

        for (char vowel : "aeiouAEIOU".toCharArray()) {
            current = current.replace(String.valueOf(vowel), "");
            if (current.length() != originalLength) return true;
        }
        return false;
    }
}
