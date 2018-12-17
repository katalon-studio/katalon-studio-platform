package com.katalon.platform.api;

public interface ExtensionPoint {
    String getPluginId();

    String getExtensionPointId();

    String getInterfaceClassName();

    ExtensionListener getServiceClass();
}
