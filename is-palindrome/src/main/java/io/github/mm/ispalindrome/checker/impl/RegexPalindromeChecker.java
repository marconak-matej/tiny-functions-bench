package io.github.mm.ispalindrome.checker.impl;

import io.github.mm.ispalindrome.checker.PalindromeChecker;
import java.util.regex.Pattern;

public class RegexPalindromeChecker implements PalindromeChecker {
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-zA-Z0-9]");

    @Override
    public boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        String cleaned = NON_ALPHANUMERIC.matcher(text).replaceAll("").toLowerCase();

        if (cleaned.isEmpty()) {
            return false;
        }

        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
