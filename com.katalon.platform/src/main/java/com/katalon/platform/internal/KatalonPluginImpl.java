package com.katalon.platform.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.ExtensionPoint;
import com.katalon.platform.api.Plugin;

public class KatalonPluginImpl implements Plugin {

    private final String pluginId;

    private final Set<Extension> extensionList = new LinkedHashSet<>();

    private final Set<ExtensionPoint> extensionPointList = new LinkedHashSet<>();

    public KatalonPluginImpl(String pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public String getPluginId() {
        return pluginId;
    }

    @Override
    public Collection<Extension> getExtensions() {
        return Collections.unmodifiableSet(extensionList);
    }

    @Override
    public Collection<ExtensionPoint> getExtensionsPoint() {
        return Collections.unmodifiableSet(extensionPointList);
    }

    public void addExtension(Extension extension) {
        extensionList.add(extension);
    }

    public void addExtensionPoint(ExtensionPoint extensionPoint) {
        extensionPointList.add(extensionPoint);
    }
}
