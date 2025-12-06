package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;
import java.util.Set;

public class AnyMatchContainsVowelChecker implements VowelChecker {
    private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        return text.chars().mapToObj(ch -> (char) ch).anyMatch(VOWELS::contains);
    }
}
