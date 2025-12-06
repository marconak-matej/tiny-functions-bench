package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;

public class ByteMaskVowelChecker implements VowelChecker {
    // Using bit manipulation for efficient lookup
    // Each bit represents a character (1 for vowel, 0 for non-vowel)
    private static final long LOWERCASE_MASK;
    private static final long UPPERCASE_MASK;

    static {
        // Create bit masks for vowels
        // For lowercase: set bits for 'a' (97), 'e' (101), 'i' (105), 'o' (111), 'u' (117)
        LOWERCASE_MASK =
                (1L << ('a' - 97)) | (1L << ('e' - 97)) | (1L << ('i' - 97)) | (1L << ('o' - 97)) | (1L << ('u' - 97));

        // For uppercase: set bits for 'A' (65), 'E' (69), 'I' (73), 'O' (79), 'U' (85)
        UPPERCASE_MASK =
                (1L << ('A' - 65)) | (1L << ('E' - 65)) | (1L << ('I' - 65)) | (1L << ('O' - 65)) | (1L << ('U' - 65));
    }

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        var length = text.length();
        for (var i = 0; i < length; i++) {
            var c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if ((LOWERCASE_MASK & (1L << (c - 97))) != 0) {
                    return true;
                }
            } else if (c >= 'A' && c <= 'Z') {
                if ((UPPERCASE_MASK & (1L << (c - 65))) != 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
