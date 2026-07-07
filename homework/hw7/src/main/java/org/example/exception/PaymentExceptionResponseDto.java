package org.example.exception;

public record PaymentExceptionResponseDto(
        Integer code,
        String message
) {}


