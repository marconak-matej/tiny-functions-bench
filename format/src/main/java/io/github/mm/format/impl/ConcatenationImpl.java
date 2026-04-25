package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;

public class ConcatenationImpl implements OrderSummaryFormatter {

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return "Order " + orderId + " for " + customer + ": $" + String.format("%.2f", amount);
    }
}

