package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.Plugin;

/**
 * PluginManager is an interface that maintains list of registered <i>Plugin</id>
 * <p>
 * The unique instance of PluginManager can access by using {@link Application#getPluginManager()}
 * 
 * @since 1.0.4
 */
public interface PluginManager {

    /**
     * @param pluginId unique id of a plugin.
     * @return an unique instance of Plugin, that id is the same with <code>pluginId</code>. Null if pluginId could not
     * be found.
     * 
     * @since 1.0.4
     */
    Plugin getPlugin(String pluginId);
}
