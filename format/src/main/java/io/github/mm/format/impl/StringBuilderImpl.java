package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;

public class StringBuilderImpl implements OrderSummaryFormatter {

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return new StringBuilder()
                .append("Order ")
                .append(orderId)
                .append(" for ")
                .append(customer)
                .append(": $")
                .append(String.format("%.2f", amount))
                .toString();
    }
}

