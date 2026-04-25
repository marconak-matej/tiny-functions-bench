package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;
import java.text.NumberFormat;
import java.util.Locale;

public class ConcatenationWithNumberFormatImpl implements OrderSummaryFormatter {

    private static final ThreadLocal<NumberFormat> NUMBER_FORMAT = ThreadLocal.withInitial(() -> {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);  // Disable thousand separators (1,000.00 → 1000.00)
        return nf;
    });

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return "Order " + orderId + " for " + customer + ": $" + NUMBER_FORMAT.get().format(amount);
    }
}

