package com.katalon.platform.internal;

import org.eclipse.e4.core.contexts.IEclipseContext;

public class EclipseContextService {

    private static IEclipseContext platformContext;

    private static IEclipseContext workbenchContext;

    public static void lookupPlatformContext(IEclipseContext context) {
        platformContext = context;
    }
    
    public static void lookupWorkbenchContext(IEclipseContext context) {
        workbenchContext = context;
    }

    public static <T> T getPlatformService(Class<T> clazz) {
        return platformContext != null ? platformContext.get(clazz) : null;
    }

    public static <T> T getWorkbenchService(Class<T> clazz) {
        return workbenchContext != null ? workbenchContext.get(clazz) : null;
    }
}
