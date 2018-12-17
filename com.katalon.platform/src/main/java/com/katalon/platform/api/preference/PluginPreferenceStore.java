package com.katalon.platform.api.preference;

import java.io.File;
import java.io.IOException;

import com.katalon.platform.internal.util.PropertySettingStoreUtil;

public class PluginPreferenceStore {
	private String bundleId;

    private String parentSettingFolder;

    private String projectDir;

    public PluginPreferenceStore(String projectDir, String bundleId) {
        this.bundleId = bundleId;
        this.projectDir = projectDir;
        this.parentSettingFolder = PropertySettingStoreUtil.EXTERNAL_SETTING_ROOT_FOLDER_NAME;
    }

    protected File getPropertyFile() throws IOException {
        File file = new File(projectDir,
                parentSettingFolder + File.separator + bundleId + PropertySettingStoreUtil.PROPERTY_FILE_EXENSION);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public void setProperty(String key, int value) throws IOException {
        PropertySettingStoreUtil.addNewProperty(key, PropertySettingStoreUtil.getRawValue(value), getPropertyFile());
    }

    public void setProperty(String key, String value) throws IOException {
        PropertySettingStoreUtil.addNewProperty(key, PropertySettingStoreUtil.getRawValue(value), getPropertyFile());
    }

    public void setProperty(String key, boolean value) throws IOException {
        PropertySettingStoreUtil.addNewProperty(key, PropertySettingStoreUtil.getRawValue(value), getPropertyFile());
    }

    private Object getValue(String key, Class<?> castType, Object defaultValueIfNotDefined) throws IOException {
        Object storedValue = PropertySettingStoreUtil
                .getValue(PropertySettingStoreUtil.getPropertyValue(key, getPropertyFile()));
        if (castType != null && castType.isInstance(storedValue)) {
            return storedValue;
        }

        return defaultValueIfNotDefined;
    }

    public boolean getBoolean(String key, boolean defaultValue) throws IOException {
        return (boolean) getValue(key, Boolean.class, defaultValue);
    }

    public int getInt(String key, int defaultValue) throws IOException {
        return (int) getValue(key, Integer.class, defaultValue);
    }

    public String getString(String key, String defaultValue) throws IOException {
        return (String) getValue(key, String.class, defaultValue);
    }

}
