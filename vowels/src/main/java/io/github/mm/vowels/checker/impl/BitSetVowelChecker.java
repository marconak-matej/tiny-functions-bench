package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;
import java.util.BitSet;

public class BitSetVowelChecker implements VowelChecker {
    private static final BitSet VOWELS = new BitSet(128);

    static {
        VOWELS.set('a');
        VOWELS.set('e');
        VOWELS.set('i');
        VOWELS.set('o');
        VOWELS.set('u');
        VOWELS.set('A');
        VOWELS.set('E');
        VOWELS.set('I');
        VOWELS.set('O');
        VOWELS.set('U');
    }

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        for (var i = 0; i < text.length(); i++) {
            if (VOWELS.get(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
