package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class StreamIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int start = 0;
        if (text.charAt(0) == '+' || text.charAt(0) == '-') {
            if (text.length() == 1) {
                return false;
            }
            start = 1;
        }

        boolean allDigits = text.substring(start).chars().allMatch(c -> c >= '0' && c <= '9');
        if (!allDigits) {
            return false;
        }

        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
