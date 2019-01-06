package com.katalon.platform.api.service;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.PluginConsoleOptionContributor;

public interface ConsoleOptionManager {
	
	public PluginConsoleOptionContributor getConsoleOption(String projectId, String pluginId) throws ResourceException;
}
