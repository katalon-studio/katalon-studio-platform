package com.katalon.platform.api;

import com.katalon.platform.api.lifecycle.ExtensionListener;

public interface ExtensionPoint {
    String getPluginId();

    String getExtensionPointId();

    String getInterfaceClassName();

    ExtensionListener getServiceClass();
}
