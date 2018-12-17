package com.katalon.platform.api;

import java.util.Collection;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionPoint;

public interface Plugin {
    String pluginId();

    Collection<Extension> extensions();

    Collection<ExtensionPoint> extensionsPoint();
}
