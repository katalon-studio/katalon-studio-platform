package com.katalon.platform.internal.ui.preference;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.extension.PluginPreferencePage;
import com.katalon.platform.api.lifecycle.ExtensionListener;

public class PluginPreferencePageExtensionListener implements ExtensionListener {

    private Map<String, PluginPreferencePage> lookup = new HashMap<>();

    @Override
    public void register(Extension extension) {
        if (extension.getImplementationClass() instanceof PluginPreferencePage) {
            PluginPreferencePage pluginPreferencePage = (PluginPreferencePage) extension.getImplementationClass();
            lookup.put(extension.getExtensionId(), pluginPreferencePage);
        }
    }

    @Override
    public void deregister(Extension extension) {
        if (extension.getImplementationClass() instanceof PluginPreferencePage) {
            if (lookup.containsKey(extension.getExtensionId())) {
                lookup.remove(extension.getExtensionId());
            }
        }
    }
}
