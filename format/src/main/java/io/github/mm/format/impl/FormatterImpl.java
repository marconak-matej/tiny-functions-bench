package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;
import java.util.Formatter;

public class FormatterImpl implements OrderSummaryFormatter {

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        StringBuilder sb = new StringBuilder();
        try (Formatter formatter = new Formatter(sb)) {
            formatter.format("Order %s for %s: $%.2f", orderId, customer, amount);
        }
        return sb.toString();
    }
}

