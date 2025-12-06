package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class OptimizedTryCatchIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int length = text.length();
        char first = text.charAt(0);

        if (first == '-' || first == '+') {
            if (length == 1) {
                return false;
            }
            char second = text.charAt(1);
            if (second < '0' || second > '9') {
                return false;
            }
        } else if (first < '0' || first > '9') {
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
