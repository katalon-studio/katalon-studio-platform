package com.katalon.platform.api.service;

import com.katalon.platform.api.extension.PluginConsoleOptionRegister;

public interface ConsoleManager {

	void registerConsoleOption(String pluginId, PluginConsoleOptionRegister pluginConsoleOptionRegister);
	
	PluginConsoleOptionRegister getRegisteredConsoleOption(String pluginId);
	
	void deregisterConsoleOption(String pluginId);
	
}
