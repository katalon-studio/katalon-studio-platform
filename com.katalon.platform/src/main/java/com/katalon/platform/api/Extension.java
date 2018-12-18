package com.katalon.platform.api;

public interface Extension {
    String getPluginId();

    String getExtensionId();

    String getExtensionPointId();

    Object getImplementationClass();
}
