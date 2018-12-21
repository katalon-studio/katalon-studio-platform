package com.katalon.platform.api.extension;

import org.eclipse.jface.preference.PreferencePage;

public interface PluginPreferencePage {

    String getName();

    String getPageId();

    PreferencePage getPage();
}
