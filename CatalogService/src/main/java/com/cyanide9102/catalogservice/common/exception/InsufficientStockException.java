package com.cyanide9102.catalogservice.common.exception;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {
    private final String bookId;
    private final int requested;
    private final int available;

    public InsufficientStockException(String message, String bookId, int requested, int available) {

        super(message);
        this.bookId = bookId;
        this.requested = requested;
        this.available = available;
    }
}
