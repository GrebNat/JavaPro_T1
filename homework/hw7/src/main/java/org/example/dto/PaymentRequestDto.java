package org.example.dto;

import lombok.Getter;

import java.math.BigDecimal;

public record PaymentRequestDto(
        Integer productId,
        BigDecimal paymentValue
) {
}
