package com.katalon.platform.api.exception;

public class CryptoException extends PlatformException {
    private static final long serialVersionUID = 8570380191861679739L;

    public CryptoException(Exception rootCause) {
        super(rootCause);
    }

}
