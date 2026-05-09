package com.cyanide9102.orderservice.common.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {

        super(message);
    }
}
