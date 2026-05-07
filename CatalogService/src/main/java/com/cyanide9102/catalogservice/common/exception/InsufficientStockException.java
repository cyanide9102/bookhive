package com.cyanide9102.catalogservice.common.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {

        super(message);
    }
}
