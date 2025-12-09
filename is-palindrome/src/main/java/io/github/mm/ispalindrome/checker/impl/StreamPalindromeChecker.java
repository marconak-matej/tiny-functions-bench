package io.github.mm.ispalindrome.checker.impl;

import io.github.mm.ispalindrome.checker.PalindromeChecker;
import java.util.stream.IntStream;

public class StreamPalindromeChecker implements PalindromeChecker {
    @Override
    public boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int length = text.length();
        return IntStream.range(0, length / 2)
                .allMatch(i -> text.charAt(i) == text.charAt(length - 1 - i));
    }
}
