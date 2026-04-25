package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;
import java.text.DecimalFormat;

public class ConcatenationWithDecimalFormatImpl implements OrderSummaryFormatter {

    private static final ThreadLocal<DecimalFormat> DECIMAL_FORMAT =
            ThreadLocal.withInitial(() -> new DecimalFormat("0.00"));

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return "Order " + orderId + " for " + customer + ": $" + DECIMAL_FORMAT.get().format(amount);
    }
}

