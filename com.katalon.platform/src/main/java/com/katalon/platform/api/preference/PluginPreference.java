package com.katalon.platform.api.preference;

/**
 * PluginPreference is a key-value store for a Katalon Studio platform plugin to manage settings value.
 * 
 * @since 1.0
 */
public interface PluginPreference extends Preference {
    String getProjectId();

    String getPluginId();
}