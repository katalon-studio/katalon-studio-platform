package com.katalon.platform.internal;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.service.ConsoleManager;
import com.katalon.platform.api.service.ControllerManager;
import com.katalon.platform.api.service.ExtensionManager;
import com.katalon.platform.api.service.PluginManager;
import com.katalon.platform.api.service.PreferenceManager;
import com.katalon.platform.api.service.ProjectManager;
import com.katalon.platform.api.service.UIServiceManager;

public class ApplicationImpl implements Application {

    private final PluginManager pluginManager = new PluginManagerImpl();

    private final ExtensionManager extensionManager = new ExtensionManagerImpl();

    private final ProjectManager projectManager = new ProjectManagerImpl();

    private final PreferenceManager preferenceManager = new PreferenceManagerImpl();

    private ControllerManager controllerManager;
    
    private UIServiceManager uiServiceManager;
    
    private ConsoleManager consoleManager = new ConsoleManagerImpl();

    @Override
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public ExtensionManager getExtensionManager() {
        return extensionManager;
    }

    @Override
    public ProjectManager getProjectManager() {
        return projectManager;
    }

    @Override
    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    @Override
    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public void setControllerManager(ControllerManager controllerManager) {
        this.controllerManager = controllerManager;
    }

    @Override
    public UIServiceManager getUIServiceManager() {
        return uiServiceManager;
    }

    public void setUIServiceManager(UIServiceManager uiServiceManager) {
        this.uiServiceManager = uiServiceManager;
    }

	@Override
	public ConsoleManager getConsoleManager() {
		return consoleManager;
	}
}
