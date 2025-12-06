package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class CharArrayIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        char[] chars = text.toCharArray();
        int length = chars.length;
        int i = 0;
        boolean negative = false;

        if (chars[0] == '-') {
            if (length == 1) {
                return false;
            }
            negative = true;
            i = 1;
        } else if (chars[0] == '+') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }

        long result = 0;
        while (i < length) {
            char c = chars[i++];
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
