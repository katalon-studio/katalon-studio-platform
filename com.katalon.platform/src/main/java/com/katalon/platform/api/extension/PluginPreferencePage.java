package com.katalon.platform.api.extension;

import org.eclipse.jface.preference.PreferencePage;

public interface PluginPreferencePage {
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.pluginPreferencePage";

    String getName();

    String getPageId();

    Class<? extends PreferencePage> getPreferencePageClass();
}
