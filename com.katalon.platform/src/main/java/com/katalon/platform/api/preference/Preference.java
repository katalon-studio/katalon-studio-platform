package com.katalon.platform.api.preference;

import com.katalon.platform.api.exception.CryptoException;
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

    void setString(String key, String value, boolean shouldEncrypt) throws CryptoException;

    void setInt(String key, int value);

    void setInt(String key, int value, boolean shouldEncrypt) throws CryptoException;

    void setBoolean(String key, boolean value);

    void setBoolean(String key, boolean value, boolean shouldEncrypt) throws CryptoException;

    String getString(String key, String defaultValue) throws InvalidDataTypeFormatException;

    String getString(String key, String defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException;

    int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException;

    int getInt(String key, int defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException;

    boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException;

    boolean getBoolean(String key, boolean defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException;

    void save() throws ResourceException;
}
