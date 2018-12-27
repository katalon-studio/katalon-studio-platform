package com.katalon.platform.api;

import com.katalon.platform.api.service.ControllerManager;
import com.katalon.platform.api.service.ExtensionManager;
import com.katalon.platform.api.service.PluginManager;
import com.katalon.platform.api.service.PreferenceManager;
import com.katalon.platform.api.service.ProjectManager;

public interface Application {

    PluginManager getPluginManager();

    ExtensionManager getExtensionManager();

    ProjectManager getProjectManager();

    PreferenceManager getPreferenceManager();

    ControllerManager getControllerManager();
}
