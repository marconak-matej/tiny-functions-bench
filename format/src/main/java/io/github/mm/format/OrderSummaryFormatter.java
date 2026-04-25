package io.github.mm.format;

/**
 * Interface for formatting order summary strings.
 * Each implementation must produce output in the format: "Order {orderId} for {customer}: ${amount}"
 * where amount is formatted to exactly 2 decimal places.
 */
public interface OrderSummaryFormatter {

    /**
     * Builds an order summary string with the given parameters.
     *
     * @param orderId the order ID
     * @param customer the customer name
     * @param amount the order amount (formatted to 2 decimal places)
     * @return the formatted order summary string
     */
    String buildOrderSummary(String orderId, String customer, double amount);
}

