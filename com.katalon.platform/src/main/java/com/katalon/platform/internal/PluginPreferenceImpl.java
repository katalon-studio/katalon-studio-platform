package com.katalon.platform.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.katalon.platform.api.exception.InvalidDataTypeFormatException;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.preference.PluginPreference;
import com.katalon.platform.internal.util.LinkedProperties;
import com.katalon.platform.internal.util.PropertySettingStoreUtil;

public class PluginPreferenceImpl implements PluginPreference {
    private final String projectId;

    private final String pluginId;

    private final LinkedProperties properties;

    private String projectDir;

    public PluginPreferenceImpl(String projectId, String pluginId) throws ResourceException {
        this.projectId = projectId;
        this.pluginId = pluginId;
        try {
            this.properties = loadProperties(getPropertyFile());
        } catch (IOException e) {
            throw new ResourceException(String.format("Unable to load settings for plugin %s", pluginId), e);
        }
    }

    private LinkedProperties loadProperties(File file) throws IOException {
        if (!file.exists()) {
            return new LinkedProperties();
        }
        try (FileInputStream fileInput = new FileInputStream(file)) {
            LinkedProperties properties = new LinkedProperties();
            properties.load(new InputStreamReader(fileInput, StandardCharsets.UTF_8));
            return properties;
        }
    }

    private File getPropertyFile() throws IOException {
        return new File(projectDir, "settings/internal/" + pluginId + ".properties");
    }

    @Override
    public String getProjectId() {
        return projectId;
    }

    @Override
    public String getPluginId() {
        return pluginId;
    }

    @Override
    public void setInt(String key, int value) {
        properties.setProperty(key, PropertySettingStoreUtil.getRawValue(value));
    }

    @Override
    public void setBoolean(String key, boolean value) {
        properties.setProperty(key, PropertySettingStoreUtil.getRawValue(value));
    }

    @Override
    public void setString(String key, String value) {
        properties.setProperty(key, PropertySettingStoreUtil.getRawValue(value));
    }

    @Override
    public String getString(String key, String defaultValue) throws InvalidDataTypeFormatException {
        if (properties.contains(key)) {
            Object value = properties.get(key);
            if (value instanceof String) {
                return (String) value;
            }
            throw new InvalidDataTypeFormatException(String.format("Value of %s is not a string", key));
        }
        return defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException {
        if (properties.contains(key)) {
            Object value = PropertySettingStoreUtil.getValue(properties.getProperty(key));
            if (value instanceof Integer) {
                return (Integer) value;
            }
            throw new InvalidDataTypeFormatException(String.format("Value of %s is not an integer", key));
        }
        return defaultValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException {
        if (properties.contains(key)) {
            Object value = properties.get(key);
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            throw new InvalidDataTypeFormatException(String.format("Value of %s is not a boolean", key));
        }
        return defaultValue;
    }

    @Override
    public void save() throws ResourceException {
        FileOutputStream fos = null;
        try {
            File propertyFile = getPropertyFile();
            if (!propertyFile.exists()) {
                propertyFile.createNewFile();
            }
            fos = new FileOutputStream(propertyFile);
            properties.store(new OutputStreamWriter(fos, StandardCharsets.UTF_8), "");
        } catch (IOException ex) {
            throw new ResourceException(String.format("Unable to save setting file for plugin: %s", pluginId));
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignore) {}
            }
        }
    }

}
