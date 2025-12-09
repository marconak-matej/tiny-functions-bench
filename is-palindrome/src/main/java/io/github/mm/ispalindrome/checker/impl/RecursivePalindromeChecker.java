package io.github.mm.ispalindrome.checker.impl;

import io.github.mm.ispalindrome.checker.PalindromeChecker;

public class RecursivePalindromeChecker implements PalindromeChecker {
    @Override
    public boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return isPalindromeRecursive(text, 0, text.length() - 1);
    }

    private boolean isPalindromeRecursive(String text, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }
        return isPalindromeRecursive(text, left + 1, right - 1);
    }
}
