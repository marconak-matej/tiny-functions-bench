package io.github.mm.vowels.checker.impl;

import io.github.mm.vowels.checker.VowelChecker;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class IntersectionForVowelChecker implements VowelChecker {
    private static final String VOWELS = "aeiouAEIOU";
    private static final Set<Character> VOWEL_SET =
            VOWELS.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());

    @Override
    public boolean hasVowels(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        var textChars = new HashSet<>();

        for (var c : text.toCharArray()) {
            textChars.add(c);
        }

        textChars.retainAll(VOWEL_SET);
        return !textChars.isEmpty();
    }
}
