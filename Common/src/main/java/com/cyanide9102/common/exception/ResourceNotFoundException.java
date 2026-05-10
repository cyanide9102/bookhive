package com.cyanide9102.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String message, String resourceType, String resourceId) {

        super(message);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}
