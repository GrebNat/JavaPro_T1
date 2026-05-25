package org.example.exceptions;

public class BadTestClassError extends RuntimeException {
    public BadTestClassError(String message) {
        super(message);
    }
}
