package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.console.PluginConsoleOption;
import com.katalon.platform.api.extension.PluginConsoleOptionRegister;
import com.katalon.platform.api.service.ConsoleManager;

public class ConsoleManagerImpl implements ConsoleManager {

	private Map<String, Map<String, Object>> map = new HashMap<>();
	
	@Override
	public void registerConsoleOption(String pluginId, PluginConsoleOptionRegister pluginConsoleOptionRegister) {
		Map<String, Object> innerMap = new HashMap<>();
		innerMap.put(PluginConsoleOptionRegister.class.getName(), pluginConsoleOptionRegister);
		map.put(pluginId, innerMap);
	}

	@Override
	public PluginConsoleOptionRegister getRegisteredConsoleOption(String pluginId) {
		if(map.containsKey(pluginId)){
			Map<String, Object> innerMap = map.get(pluginId);
			if(innerMap.containsKey(PluginConsoleOptionRegister.class.getName())){
				return (PluginConsoleOptionRegister) innerMap.get(PluginConsoleOptionRegister.class.getName());
			}
		}
		return null;
	}

	@Override
	public void deregisterConsoleOption(String pluginId) {
		if(map.containsKey(pluginId)){
			Map<String, Object> innerMap = map.get(pluginId);
			if(!innerMap.isEmpty()){
				innerMap.remove(PluginConsoleOption.class);
			}
		}
	}

}
