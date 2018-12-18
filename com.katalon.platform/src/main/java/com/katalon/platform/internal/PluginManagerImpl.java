package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.service.PluginManager;

public class PluginManagerImpl implements PluginManager {

    Map<String, Plugin> pluginIndice = new HashMap<>();

    @Override
    public Plugin getPlugin(String pluginId) {
        return pluginIndice.getOrDefault(pluginId, null);
    }

    public void addPlugin(Plugin plugin) {
        pluginIndice.put(plugin.getPluginId(), plugin);
    }

    public void removePlugin(Plugin plugin) {
        String pluginId = plugin.getPluginId();
        if (pluginIndice.containsKey(pluginId)) {
            pluginIndice.remove(pluginId);
        }
    }
}
