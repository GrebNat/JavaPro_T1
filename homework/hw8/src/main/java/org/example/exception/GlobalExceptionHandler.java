package org.example.exception;

import org.example.entity.UserLimitEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<org.example.exception.ExceptionResponseDto> handleInsufficientFunds(LimitExceededException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new org.example.exception.ExceptionResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }
    @ExceptionHandler(UserLimitAlreadyExistsException.class)
    public ResponseEntity<org.example.exception.ExceptionResponseDto> handleInsufficientFunds(UserLimitAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new org.example.exception.ExceptionResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
