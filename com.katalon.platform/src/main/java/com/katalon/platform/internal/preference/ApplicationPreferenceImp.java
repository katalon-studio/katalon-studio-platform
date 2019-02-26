package com.katalon.platform.internal.preference;

import java.io.IOException;

import org.eclipse.jface.preference.IPersistentPreferenceStore;

import com.katalon.platform.api.exception.InvalidDataTypeFormatException;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.preference.ApplicationPreference;

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
    public void setInt(String key, int value) {
        preferenceStore.setValue(key, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        preferenceStore.setValue(key, value);
    }

    @Override
    public String getString(String key, String defaultValue) throws InvalidDataTypeFormatException {
        return preferenceStore.contains(key) ? preferenceStore.getString(key) : defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException {
        return preferenceStore.contains(key) ? preferenceStore.getInt(key) : defaultValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException {
        return preferenceStore.contains(key) ? preferenceStore.getBoolean(key) : defaultValue;
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
