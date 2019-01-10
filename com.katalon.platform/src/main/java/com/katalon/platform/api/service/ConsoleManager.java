package com.katalon.platform.api.service;

import java.util.List;

import com.katalon.platform.api.model.PluginConsoleOption;

public interface ConsoleManager {

	void registerConsoleOptionList(String pluginId, List<PluginConsoleOption<?>> pluginConsoleOptionList);
	
	List<PluginConsoleOption<?>> getRegisteredConsoleOptionList(String pluginId);
	
	void deregisterConsoleOptionList(String pluginId);
	
}
