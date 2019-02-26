package com.katalon.platform.api.preference;

import com.katalon.platform.api.exception.InvalidDataTypeFormatException;
import com.katalon.platform.api.exception.ResourceException;

/**
 * 
 * Preference is a key-value store for manage settings value.
 * 
 * @since 1.0.7
 *
 */
public interface Preference {

    void setString(String key, String value);
    
    void setString(String key, String value, boolean encrypted);

    void setInt(String key, int value);
    
    void setInt(String key, int value, boolean encrypted);

    void setBoolean(String key, boolean value);

    void setBoolean(String key, boolean value, boolean encrypted);

    String getString(String key, String defaultValue) throws InvalidDataTypeFormatException;

    int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException;

    boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException;

    void save() throws ResourceException;
}
