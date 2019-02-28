package com.katalon.platform.internal.preference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import com.katalon.platform.api.exception.CryptoException;
import com.katalon.platform.api.exception.InvalidDataTypeFormatException;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Entity;
import com.katalon.platform.api.preference.PluginPreference;
import com.katalon.platform.internal.util.CryptoUtil;
import com.katalon.platform.internal.util.LinkedProperties;
import com.katalon.platform.internal.util.PropertySettingStoreUtil;

public class PluginPreferenceImpl implements PluginPreference {
    private final String projectId;

    private final String pluginId;

    private final LinkedProperties properties;

    private String projectDir;

    public PluginPreferenceImpl(Entity projectEntity, String pluginId) throws ResourceException {
        this.projectId = projectEntity.getId();
        this.pluginId = pluginId;
        this.projectDir = projectEntity.getFolderLocation();
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
        return new File(projectDir, "settings/external/" + pluginId + ".properties");
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
        try {
            setInt(key, value, false);
        } catch (CryptoException ignored) {}
    }

    @Override
    public void setInt(String key, int value, boolean shouldEncrypt) throws CryptoException {
        String rawValue = PropertySettingStoreUtil.getRawValue(value);
        if (shouldEncrypt) {
            try {
                rawValue = CryptoUtil.encode(CryptoUtil.getDefault(rawValue));
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
                throw new CryptoException(e);
            }
        }
        properties.setProperty(key, rawValue);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        try {
            setBoolean(key, value, false);
        } catch (CryptoException ignored) {}
    }

    @Override
    public void setBoolean(String key, boolean value, boolean shouldEncrypt) throws CryptoException {
        String rawValue = PropertySettingStoreUtil.getRawValue(value);
        if (shouldEncrypt) {
            try {
                rawValue = CryptoUtil.encode(CryptoUtil.getDefault(rawValue));
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
                throw new CryptoException(e);
            }
        }
        properties.setProperty(key, rawValue);
    }

    @Override
    public void setString(String key, String value) {
        try {
            setString(key, value, false);
        } catch (CryptoException ignored) {}
    }

    @Override
    public void setString(String key, String value, boolean shouldEncrypt) throws CryptoException {
        String rawValue = PropertySettingStoreUtil.getRawValue(value);
        if (shouldEncrypt) {
            try {
                rawValue = CryptoUtil.encode(CryptoUtil.getDefault(rawValue));
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
                throw new CryptoException(e);
            }
        }
        properties.setProperty(key, rawValue);
    }

    @Override
    public String getString(String key, String defaultValue) throws InvalidDataTypeFormatException {
        try {
            return getString(key, defaultValue, false);
        } catch (CryptoException e) {
            return defaultValue;
        }
    }

    @Override
    public String getString(String key, String defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException {
        if (properties.containsKey(key)) {
            Object value = getDecryptedValue(key, shouldDecrypt);
            if (value instanceof String) {
                return (String) value;
            }
            throw new InvalidDataTypeFormatException(String.format("Value of %s is not a string", key));
        }
        return defaultValue;
    }

    private Object getDecryptedValue(String key, boolean shouldDecrypt) throws CryptoException {
        String rawValue = PropertySettingStoreUtil.getRawValue(properties.get(key).toString());
        if (shouldDecrypt) {
            try {
                rawValue = CryptoUtil.decode(CryptoUtil.getDefault(rawValue));
            } catch (GeneralSecurityException | IOException e) {
                throw new CryptoException(e);
            }
        }
        return PropertySettingStoreUtil.getValue(rawValue);
    }

    @Override
    public int getInt(String key, int defaultValue) throws InvalidDataTypeFormatException {
        try {
            return getInt(key, defaultValue, false);
        } catch (CryptoException ignored) {
            return defaultValue;
        }
    }

    @Override
    public int getInt(String key, int defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException {
        if (properties.containsKey(key)) {
            Object value = getDecryptedValue(key, shouldDecrypt);
            if (value instanceof Integer) {
                return (Integer) value;
            }
            throw new InvalidDataTypeFormatException(String.format("Value of %s is not an integer", key));
        }
        return defaultValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) throws InvalidDataTypeFormatException {
        try {
            return getBoolean(key, defaultValue, false);
        } catch (CryptoException ignored) {
            return defaultValue;
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue, boolean shouldDecrypt)
            throws InvalidDataTypeFormatException, CryptoException {
        if (properties.containsKey(key)) {
            Object value = getDecryptedValue(key, shouldDecrypt);
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
