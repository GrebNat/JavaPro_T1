package org.example.exception;

public class InvalidPaymentAmountException extends RuntimeException {
    public InvalidPaymentAmountException() {
        super("На счете недостаточно средств");
    }
}
