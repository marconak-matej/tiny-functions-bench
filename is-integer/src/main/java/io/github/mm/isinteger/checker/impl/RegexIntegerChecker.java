package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;
import java.util.regex.Pattern;

public class RegexIntegerChecker implements IntegerChecker {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[+-]?\\d+$");

    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        if (!INTEGER_PATTERN.matcher(text).matches()) {
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
