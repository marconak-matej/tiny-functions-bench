package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class ManualParsingIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int length = text.length();
        int i = 0;
        boolean negative = false;

        if (text.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            negative = true;
            i = 1;
        } else if (text.charAt(0) == '+') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }

        if (i >= length) {
            return false;
        }

        long result = 0;
        while (i < length) {
            char c = text.charAt(i++);
            if (c < '0' || c > '9') {
                return false;
            }
            result = result * 10 + (c - '0');
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
