package com.katalon.platform.internal.preference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.eclipse.jface.preference.IPersistentPreferenceStore;

import com.katalon.platform.api.exception.CryptoException;
import com.katalon.platform.api.exception.InvalidDataTypeFormatException;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.preference.ApplicationPreference;
import com.katalon.platform.internal.util.CryptoUtil;

public class ApplicationPreferenceImp implements ApplicationPreference {

    private final IPersistentPreferenceStore preferenceStore;

    private final String pluginId;

    public ApplicationPreferenceImp(String pluginId, IPersistentPreferenceStore preferenceStore) {
        this.pluginId = pluginId;
        this.preferenceStore = preferenceStore;
    }

    @Override
    public void setString(String key, String value) {
        preferenceStore.setValue(key, value);
    }

    @Override
    public void setString(String key, String value, boolean shouldEncrypt) throws CryptoException {
        try {
            String encryptedValue = CryptoUtil.encode(CryptoUtil.getDefault(value));
            preferenceStore.setValue(key, encryptedValue);
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public void setInt(String key, int value) {
        preferenceStore.setValue(key, value);
    }

    @Override
    public void setInt(String key, int value, boolean shouldEncrypt) throws CryptoException {
        try {
            String encryptedValue = CryptoUtil.encode(CryptoUtil.getDefault(Integer.toString(value)));
            preferenceStore.setValue(key, encryptedValue);
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public void setBoolean(String key, boolean value) {
        preferenceStore.setValue(key, value);
    }

    @Override
    public void setBoolean(String key, boolean value, boolean shouldEncrypt) throws CryptoException {
        try {
            String encryptedValue = CryptoUtil.encode(CryptoUtil.getDefault(Boolean.toString(value)));
            preferenceStore.setValue(key, encryptedValue);
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public String getString(String key, String defaultValue) throws InvalidDataTypeFormatException {
        return preferenceStore.contains(key) ? preferenceStore.getString(key) : defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException {
        String rawValue = preferenceStore.contains(key) ? preferenceStore.getString(key) : defaultValue;
        try {
            return CryptoUtil.decode(CryptoUtil.getDefault(rawValue));
        } catch (GeneralSecurityException | IOException e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException {
        return preferenceStore.contains(key) ? preferenceStore.getInt(key) : defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException {
        String rawValue = preferenceStore.contains(key) ? preferenceStore.getString(key)
                : Integer.toString(defaultValue);
        String decryptedValue = "";
        try {
            decryptedValue = CryptoUtil.decode(CryptoUtil.getDefault(rawValue));
            return Integer.valueOf(decryptedValue);
        } catch (GeneralSecurityException | IOException e) {
            throw new CryptoException(e);
        } catch (NumberFormatException e) {
            throw new InvalidDataTypeFormatException(
                    "Decrypted value: " + decryptedValue + " of key: " + key + " is invalid number");
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException {
        return preferenceStore.contains(key) ? preferenceStore.getBoolean(key) : defaultValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException {
        String rawValue = preferenceStore.contains(key) ? preferenceStore.getString(key)
                : Boolean.toString(defaultValue);
        String decryptedValue = "";
        try {
            decryptedValue = CryptoUtil.decode(CryptoUtil.getDefault(rawValue));
            if (!Boolean.parseBoolean(decryptedValue)) {
                throw new InvalidDataTypeFormatException(
                        "Decrypted value: " + decryptedValue + " of key: " + key + " is invalid boolean");
            }
            return Boolean.valueOf(decryptedValue);
        } catch (GeneralSecurityException | IOException e) {
            throw new CryptoException(e);
        }
    }

    @Override
    public void save() throws ResourceException {
        if (preferenceStore.needsSaving()) {
            try {
                preferenceStore.save();
            } catch (IOException e) {
                throw new ResourceException("Unable to update settings for application plugin: " + pluginId, e);
            }
        }
    }

    @Override
    public String getPluginId() {
        return pluginId;
    }
}
