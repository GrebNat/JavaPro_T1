package org.example.dto;

import org.example.entity.User;

import java.math.BigDecimal;

public record ProductResponseDto (
        Integer id,
        String account,
        BigDecimal balance,
        String productType,
        User user
){}
