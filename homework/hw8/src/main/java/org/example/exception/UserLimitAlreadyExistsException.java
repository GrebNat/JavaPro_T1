package org.example.exception;

public class UserLimitAlreadyExistsException extends RuntimeException {
    public UserLimitAlreadyExistsException(String message) {
        super(message);
    }
}
