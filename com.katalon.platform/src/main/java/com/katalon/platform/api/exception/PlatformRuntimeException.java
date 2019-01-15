package com.katalon.platform.api.exception;

public class PlatformRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String detailMessage;

    private final RuntimeException rootCause;

    public PlatformRuntimeException(String message) {
        this(message, null);
    }

    public PlatformRuntimeException(RuntimeException rootCause) {
        this("", rootCause);
    }

    public PlatformRuntimeException(String message, RuntimeException rootCause) {
        super(rootCause);
        this.detailMessage = message;
        this.rootCause = rootCause;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public RuntimeException getCause() {
        return rootCause;
    }
    
    @Override
    public String toString() {
        return String.format("PlatformRuntimeException { detailMessage : %s, rootCause %s }",
                detailMessage != null ? detailMessage : "null",
                rootCause != null ? rootCause.toString() : "null");
    }
}
