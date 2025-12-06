package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class CharAtIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int length = text.length();
        int start = 0;
        boolean negative = false;

        char first = text.charAt(0);
        if (first == '-') {
            if (length == 1) {
                return false;
            }
            negative = true;
            start = 1;
        } else if (first == '+') {
            if (length == 1) {
                return false;
            }
            start = 1;
        }

        long result = 0;
        for (int i = start; i < length; i++) {
            char c = text.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }

            int digit = c - '0';
            result = result * 10 + digit;

            if (negative) {
                if (-result < Integer.MIN_VALUE) {
                    return false;
                }
            } else {
                if (result > Integer.MAX_VALUE) {
                    return false;
                }
            }
        }

        return true;
    }
}
