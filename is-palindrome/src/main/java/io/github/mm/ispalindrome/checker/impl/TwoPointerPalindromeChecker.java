package io.github.mm.ispalindrome.checker.impl;

import io.github.mm.ispalindrome.checker.PalindromeChecker;

public class TwoPointerPalindromeChecker implements PalindromeChecker {
    @Override
    public boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
