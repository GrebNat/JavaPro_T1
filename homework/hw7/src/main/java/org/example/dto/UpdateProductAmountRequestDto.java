package org.example.dto;

import java.math.BigDecimal;

public record UpdateProductAmountRequestDto(
        BigDecimal amount
) {
}
