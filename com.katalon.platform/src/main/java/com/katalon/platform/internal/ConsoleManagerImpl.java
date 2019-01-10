package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.katalon.platform.api.model.PluginConsoleOption;
import com.katalon.platform.api.service.ConsoleManager;

public class ConsoleManagerImpl implements ConsoleManager {

	private Map<String, Map<Class<?>, Object>> map = new HashMap<>();
	
	@Override
	public void registerConsoleOptionList(String pluginId, List<PluginConsoleOption<?>> pluginConsoleOptionList) {
		Map<Class<?>, Object> innerMap = new HashMap<>();
		innerMap.put(PluginConsoleOption.class, pluginConsoleOptionList);
		map.put(pluginId, innerMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PluginConsoleOption<?>> getRegisteredConsoleOptionList(String pluginId) {
		if(map.containsKey(pluginId)){
			Map<Class<?>, Object> innerMap = map.get(pluginId);
			if(!innerMap.isEmpty()){
				return (List<PluginConsoleOption<?>>) innerMap.get(PluginConsoleOption.class);
			}
		}
		return null;
	}

	@Override
	public void deregisterConsoleOptionList(String pluginId) {
		if(map.containsKey(pluginId)){
			Map<Class<?>, Object> innerMap = map.get(pluginId);
			if(!innerMap.isEmpty()){
				innerMap.remove(PluginConsoleOption.class);
			}
		}
	}

}
