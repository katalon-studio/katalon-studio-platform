package com.katalon.platform.api.extension;

public interface Extension {
    String pluginId();

    String extensionId();

    String extensionPointId();

    Object implementationClass();
}
