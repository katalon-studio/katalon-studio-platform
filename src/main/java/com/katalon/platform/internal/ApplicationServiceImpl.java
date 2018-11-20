package com.katalon.platform.internal;

import org.osgi.framework.BundleContext;

public class ApplicationServiceImpl {
    private static BundleContext bundleContext;
    
    public static void lookupBundleContext(BundleContext context) {
        bundleContext = context;
    }
    
    public static <T> T get(Class<T> clazz) {
        return bundleContext.getService(bundleContext.getServiceReference(clazz));
    }
}
