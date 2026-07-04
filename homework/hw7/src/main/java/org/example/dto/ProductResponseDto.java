package org.example.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String account,
        BigDecimal balance,
        String productType,
        Integer userId
){}
