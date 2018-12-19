package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.preference.PluginPreference;

/**
 * Provides services to get {@link PluginPreference} of a loaded plugin. </br>
 * By default, <code>PreferenceManager</code> is provided from the current {@link Application}
 * @see {@link Application#getPreferenceManager()}
 * @see {@link #getPluginPreference(String, String)}
 * 
 * @since 1.0
 */
public interface PreferenceManager {
    /**
     * @param projectId project folder location of a project
     * @param pluginId full qualifier name or symbolic name of a plugin
     * @return an instance of PluginPreference that provides ability to get, and update settings value of a plugin.
     * @throws ResourceException if <code>projectId</code>, or <code>pluginId</code> is not found or invalid.
     * 
     * @see {@link ProjectManager#getCurrentProject()}
     * 
     * @since 1.0
     */
    PluginPreference getPluginPreference(String projectId, String pluginId) throws ResourceException;
}
