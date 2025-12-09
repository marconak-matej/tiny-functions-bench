package io.github.mm.ispalindrome.checker.impl;

import io.github.mm.ispalindrome.checker.PalindromeChecker;

public class HalfStringPalindromeChecker implements PalindromeChecker {
    @Override
    public boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int length = text.length();
        int halfLength = length / 2;

        for (int i = 0; i < halfLength; i++) {
            if (text.charAt(i) != text.charAt(length - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
