package org.example.exception;

import java.time.LocalDateTime;

public record ExceptionResponseDto(
        Integer code,
        String message,
        LocalDateTime timestamp
) {}

