package io.github.mm.format;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.mm.format.impl.ConcatenationImpl;
import io.github.mm.format.impl.FormattedStringImpl;
import io.github.mm.format.impl.FormatterImpl;
import io.github.mm.format.impl.MessageFormatImpl;
import io.github.mm.format.impl.StringBuilderImpl;
import io.github.mm.format.impl.ConcatenationWithDecimalFormatImpl;
import io.github.mm.format.impl.ConcatenationWithNumberFormatImpl;
import io.github.mm.format.impl.StringFormatImpl;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderSummaryFormatterTest {

    private static Stream<Arguments> formatterProvider() {
        return Stream.of(
                Arguments.of(new FormattedStringImpl()),
                Arguments.of(new MessageFormatImpl()),
                Arguments.of(new StringBuilderImpl()),
                Arguments.of(new ConcatenationWithDecimalFormatImpl()),
                Arguments.of(new ConcatenationWithNumberFormatImpl()),
                Arguments.of(new FormatterImpl()),
                Arguments.of(new StringFormatImpl()),
                Arguments.of(new ConcatenationImpl()));
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void normalCase(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-001", "John Doe", 99.9);
        assertEquals("Order ORD-001 for John Doe: $99.90", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void zeroAmount(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-002", "Jane", 0.0);
        assertEquals("Order ORD-002 for Jane: $0.00", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void largeAmount(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-003", "Alice", 1000000.5);
        assertEquals("Order ORD-003 for Alice: $1000000.50", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void exactTwoDecimals(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-004", "Bob", 42.0);
        assertEquals("Order ORD-004 for Bob: $42.00", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void trailingDecimal(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-005", "Charlie", 123.4);
        assertEquals("Order ORD-005 for Charlie: $123.40", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void roundingDown(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-006", "Diana", 99.994);
        assertEquals("Order ORD-006 for Diana: $99.99", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void roundingUp(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-007", "Eve", 99.996);
        assertEquals("Order ORD-007 for Eve: $100.00", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void specialCharactersInNames(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-008", "O'Brien & Co.", 199.99);
        assertEquals("Order ORD-008 for O'Brien & Co.: $199.99", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void verySmallAmount(OrderSummaryFormatter formatter) {
        String result = formatter.buildOrderSummary("ORD-009", "Frank", 0.01);
        assertEquals("Order ORD-009 for Frank: $0.01", result);
    }

    @ParameterizedTest
    @MethodSource("formatterProvider")
    void negativeAmount(OrderSummaryFormatter formatter) {
        // Edge case: negative amounts (could be refunds)
        String result = formatter.buildOrderSummary("ORD-010", "Grace", -50.5);
        assertEquals("Order ORD-010 for Grace: $-50.50", result);
    }
}

