package com.cyanide9102.orderservice.common.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {

        super(message);
    }
}
