package org.example.exception;

import java.math.BigDecimal;

import static java.lang.String.format;

public class ReserveExceededException extends RuntimeException {
    public ReserveExceededException(Long userId, BigDecimal currentReserve) {
        super(format("Резерв пользователя id=%s %s. Превышен", userId, currentReserve));
    }
}
