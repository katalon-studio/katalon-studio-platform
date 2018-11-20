package com.katalon.platform.api;

import com.katalon.platform.api.service.ExtensionManager;
import com.katalon.platform.api.service.PluginManager;

public interface Application {

    public PluginManager getPluginManager();

    public ExtensionManager getExtensionManager();
}
