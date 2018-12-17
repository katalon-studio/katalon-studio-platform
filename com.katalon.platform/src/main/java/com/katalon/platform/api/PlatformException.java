package com.katalon.platform.api;

public class PlatformException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String detailMessage;

    private final Throwable rootCause;

    public PlatformException(String message) {
        this(message, null);
    }

    public PlatformException(Exception rootCause) {
        this("", rootCause);
    }

    public PlatformException(String message, Exception rootCause) {
        super(rootCause);
        this.detailMessage = message;
        this.rootCause = rootCause;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public Throwable getCause() {
        return rootCause;
    }
    
    @Override
    public String toString() {
        return String.format("PlatformException { detailMessage : %s, rootCause %s }",
                detailMessage != null ? detailMessage : "null",
                rootCause != null ? rootCause.toString() : "null");
    }
}
