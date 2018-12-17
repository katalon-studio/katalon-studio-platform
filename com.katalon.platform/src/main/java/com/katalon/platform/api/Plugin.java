package com.katalon.platform.api;

import java.util.Collection;

public interface Plugin {
    String getPluginId();

    Collection<Extension> getExtensions();

    Collection<ExtensionPoint> getExtensionsPoint();
}
