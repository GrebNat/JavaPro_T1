package org.example.dto;

import java.math.BigDecimal;

public record UpdateProductBalanceRequestDto(
        BigDecimal amount
) {
}
