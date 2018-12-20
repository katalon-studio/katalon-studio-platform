package com.katalon.platform.internal.util;

import com.katalon.platform.api.PlatformException;

public class ExceptionUtil {

    public static void wrapAndThrow(Exception e) throws PlatformException {
        throw new PlatformException(e);
    }
}
