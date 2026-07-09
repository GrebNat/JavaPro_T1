package org.example.exception;

import java.math.BigDecimal;

import static java.lang.String.format;

public class LimitExceededException extends RuntimeException {
    public LimitExceededException(Long userId, BigDecimal currentReserve) {
        super(format("Лимит пользователя id=%s - %s. Превышен резервом", userId, currentReserve));
    }
}
