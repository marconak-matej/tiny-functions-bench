package io.github.mm.format.impl;

import io.github.mm.format.OrderSummaryFormatter;
import java.text.MessageFormat;
import java.util.Locale;

public class MessageFormatImpl implements OrderSummaryFormatter {

    private static final String PATTERN = "Order {0} for {1}: ${2,number,0.00}";

    // ThreadLocal to cache MessageFormat per thread (MessageFormat is not thread-safe)
    private static final ThreadLocal<MessageFormat> MESSAGE_FORMAT = ThreadLocal.withInitial(
            () -> new MessageFormat(PATTERN, Locale.US));

    @Override
    public String buildOrderSummary(String orderId, String customer, double amount) {
        return MESSAGE_FORMAT.get().format(new Object[]{orderId, customer, amount});
    }
}

