package org.example.dto;

import java.math.BigDecimal;

public record UserLimitResponseDto(
        Long userId,
        BigDecimal currentLimit,
        BigDecimal reserved
) {
}
