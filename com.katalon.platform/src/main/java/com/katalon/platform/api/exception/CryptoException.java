package com.katalon.platform.api.exception;

/**
 * @since 1.0.8
 *
 */
public class CryptoException extends PlatformException {
    private static final long serialVersionUID = 8570380191861679739L;

    public CryptoException(Exception rootCause) {
        super(rootCause);
    }

}
