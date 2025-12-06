package io.github.mm.isinteger.checker.impl;

import io.github.mm.isinteger.checker.IntegerChecker;

public class ScannerIntegerChecker implements IntegerChecker {
    @Override
    public boolean isInteger(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        // Check for leading/trailing whitespace first
        if (!text.equals(text.trim())) {
            return false;
        }

        try (var scanner = new java.util.Scanner(text)) {
            if (scanner.hasNextInt()) {
                scanner.nextInt();
                return !scanner.hasNext();
            }
            return false;
        }
    }
}
