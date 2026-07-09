package org.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<ExceptionResponseDto> handleInsufficientFunds(LimitExceededException e) {
        return status(BAD_REQUEST).body(new ExceptionResponseDto(BAD_REQUEST.value(), e.getMessage(), now()));
    }

    @ExceptionHandler(UserLimitAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDto> handleInsufficientFunds(UserLimitAlreadyExistsException e) {
        return status(BAD_REQUEST).body(new ExceptionResponseDto(BAD_REQUEST.value(), e.getMessage(), now()));
    }

    @ExceptionHandler(ReserveExceededException.class)
    public ResponseEntity<ExceptionResponseDto> handleInsufficientFunds(ReserveExceededException e) {
        return status(BAD_REQUEST).body(new ExceptionResponseDto(BAD_REQUEST.value(), e.getMessage(), now()));
    }
}
