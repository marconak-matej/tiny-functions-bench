package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;

public class StringFormatImpl implements OrderSummaryFormatter {

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return String.format("Order %s for %s: $%.2f", orderId, customer, amount);
    }
}

