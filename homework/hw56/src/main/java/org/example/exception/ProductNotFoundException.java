package org.example.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer productId) {
        super("Продукт с id = " + productId + " не существует");
    }
}
