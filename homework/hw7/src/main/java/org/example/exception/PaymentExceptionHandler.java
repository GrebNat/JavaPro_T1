package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PaymentExceptionHandler {

    @ExceptionHandler(PaymentServiceException.class)
    public ResponseEntity<PaymentExceptionResponseDto> handleInsufficientFunds(PaymentServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new PaymentExceptionResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()
                ));
    }
}
