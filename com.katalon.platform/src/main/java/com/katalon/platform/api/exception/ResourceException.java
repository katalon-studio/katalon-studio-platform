package com.katalon.platform.api.exception;

public class ResourceException extends PlatformException {
    private static final long serialVersionUID = 1L;
    
    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Exception rootCause) {
        super(message, rootCause);
    }

}
