package com.katalon.platform.api.preference;

/**
 * ApplicationPreference is a key-value store for a Katalon Studio built-in plugin/function to manage settings value.
 * 
 * @since 1.0.7
 */
public interface ApplicationPreference extends Preference {
    String getPluginId();
}