package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;

public class FormattedStringImpl implements OrderSummaryFormatter {

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return "Order %s for %s: $%.2f".formatted(orderId, customer, amount);
    }
}

