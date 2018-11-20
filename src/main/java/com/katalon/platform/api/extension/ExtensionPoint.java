package com.katalon.platform.api.extension;

public interface ExtensionPoint {
    String pluginId();

    String extensionPointId();

    String interfaceClassName();

    ExtensionListener serviceClass();
}
