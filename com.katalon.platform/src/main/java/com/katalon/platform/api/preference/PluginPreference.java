package com.katalon.platform.api.preference;

import com.katalon.platform.api.exception.InvalidDataTypeFormatException;
import com.katalon.platform.api.exception.ResourceException;

/**
 * PluginPreference is a key-value store for a plugin to manage settings value.
 * 
 * @since 1.0
 */
public interface PluginPreference {
    String getProjectId();

    String getPluginId();

    void setString(String key, String value);

    void setInt(String key, int value);

    void setBoolean(String key, boolean value);

    String getString(String key, String defaultValue) throws InvalidDataTypeFormatException;

    int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException;

    boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException;

    void save() throws ResourceException;
}