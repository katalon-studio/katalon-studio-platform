package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.extension.PluginConsoleOptionRegister;
import com.katalon.platform.api.model.PluginConsoleOption;
import com.katalon.platform.api.service.ConsoleManager;

public class ConsoleManagerImpl implements ConsoleManager {

	private Map<String, Map<Class<?>, Object>> map = new HashMap<>();
	
	@Override
	public void registerConsoleOption(String pluginId, PluginConsoleOptionRegister pluginConsoleOptionRegister) {
		Map<Class<?>, Object> innerMap = new HashMap<>();
		innerMap.put(PluginConsoleOptionRegister.class, pluginConsoleOptionRegister);
		map.put(pluginId, innerMap);
	}

	@Override
	public PluginConsoleOptionRegister getRegisteredConsoleOption(String pluginId) {
		if(map.containsKey(pluginId)){
			Map<Class<?>, Object> innerMap = map.get(pluginId);
			if(!innerMap.isEmpty()){
				return (PluginConsoleOptionRegister) innerMap.get(PluginConsoleOptionRegister.class);
			}
		}
		return null;
	}

	@Override
	public void deregisterConsoleOption(String pluginId) {
		if(map.containsKey(pluginId)){
			Map<Class<?>, Object> innerMap = map.get(pluginId);
			if(!innerMap.isEmpty()){
				innerMap.remove(PluginConsoleOption.class);
			}
		}
	}

}
