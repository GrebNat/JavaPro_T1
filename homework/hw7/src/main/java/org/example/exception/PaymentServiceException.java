package org.example.exception;

import lombok.Getter;

@Getter
public class PaymentServiceException extends RuntimeException {
    public PaymentServiceException(String message) {
        super(message);
    }
}
