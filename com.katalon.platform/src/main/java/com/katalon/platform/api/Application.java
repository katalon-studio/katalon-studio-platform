package com.katalon.platform.api;

import com.katalon.platform.api.service.ConsoleManager;
import com.katalon.platform.api.service.ControllerManager;
import com.katalon.platform.api.service.ExtensionManager;
import com.katalon.platform.api.service.PluginManager;
import com.katalon.platform.api.service.PreferenceManager;
import com.katalon.platform.api.service.ProjectManager;
import com.katalon.platform.api.service.UIServiceManager;

public interface Application {

    PluginManager getPluginManager();

    ExtensionManager getExtensionManager();

    ProjectManager getProjectManager();

    PreferenceManager getPreferenceManager();

    ControllerManager getControllerManager();

    UIServiceManager getUIServiceManager();
    
    ConsoleManager getConsoleManager();
}