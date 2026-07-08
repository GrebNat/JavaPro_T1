package org.example.service;

import org.example.dto.LimitUpdateDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LimitService {

    public BigDecimal getLimitByUserId(Long userId) {
            return null;
    }

    public void updateUserLimit(Long userId, BigDecimal limit) {
    }

    public void updateUserLimitToDefault(Long userId) {

    }

    public void addNewUserLimit(LimitUpdateDto limitDto) {
    }
}
