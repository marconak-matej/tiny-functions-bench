package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class ApacheCommonsIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int start = 0;
        if (text.charAt(0) == '-' || text.charAt(0) == '+') {
            if (text.length() == 1) {
                return false;
            }
            start = 1;
        }

        for (int i = start; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }

        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
