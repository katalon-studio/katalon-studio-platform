package com.katalon.platform.api.service;

import java.util.Collection;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionPoint;

public interface ExtensionManager {
    Collection<Extension> getExtensions(String extensionPointId);
    
    ExtensionPoint getExtensionPoint(String extensionPointId);
}
