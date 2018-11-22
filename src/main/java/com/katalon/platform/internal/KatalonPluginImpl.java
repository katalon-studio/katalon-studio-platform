package com.katalon.platform.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionPoint;

public class KatalonPluginImpl implements Plugin {

    private final String pluginId;

    private final Set<Extension> extensionList = new LinkedHashSet<>();

    private final Set<ExtensionPoint> extensionPointList = new LinkedHashSet<>();

    public KatalonPluginImpl(String pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public String pluginId() {
        return pluginId;
    }

    @Override
    public Collection<Extension> extensions() {
        return Collections.unmodifiableSet(extensionList);
    }

    @Override
    public Collection<ExtensionPoint> extensionsPoint() {
        return Collections.unmodifiableSet(extensionPointList);
    }

    public void addExtension(Extension extension) {
        extensionList.add(extension);
    }

    public void addExtensionPoint(ExtensionPoint extensionPoint) {
        extensionPointList.add(extensionPoint);
    }
}
