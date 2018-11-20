package com.katalon.platform.api;

public class PlatformException extends Exception {

    private static final long serialVersionUID = 1L;

    public PlatformException(Exception e) {
        super(e);
    }
}
