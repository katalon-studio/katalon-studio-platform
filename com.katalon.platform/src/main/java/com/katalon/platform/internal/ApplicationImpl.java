package com.katalon.platform.internal;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.service.ConsoleOptionManager;
import com.katalon.platform.api.service.ExtensionManager;
import com.katalon.platform.api.service.PluginManager;
import com.katalon.platform.api.service.PreferenceManager;
import com.katalon.platform.api.service.ProjectManager;

public class ApplicationImpl implements Application {

    private final PluginManager pluginManager = new PluginManagerImpl();

    private final ExtensionManager extensionManager = new ExtensionManagerImpl();

    private final ProjectManager projectManager = new ProjectManagerImpl();

    private final PreferenceManager preferenceManager = new PreferenceManagerImpl();
    
    private final ConsoleOptionManager consoleOptionManager = new ConsoleOptionManagerImpl();

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
	public ConsoleOptionManager getConsoleOptionManager() {
		return consoleOptionManager;
	}
}
