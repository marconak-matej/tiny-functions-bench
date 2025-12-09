package io.github.mm.ispalindrome.checker.impl;

import io.github.mm.ispalindrome.checker.PalindromeChecker;

public class CharArrayPalindromeChecker implements PalindromeChecker {
    @Override
    public boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        char[] chars = text.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
