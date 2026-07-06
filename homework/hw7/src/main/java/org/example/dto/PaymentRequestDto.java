package org.example.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(
        Integer productId,
        BigDecimal paymentValue
) {
}
