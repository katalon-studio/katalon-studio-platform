package com.katalon.platform.api.service;

import java.util.Collection;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.ExtensionPoint;

public interface ExtensionManager {
    Collection<Extension> getExtensions(String extensionPointId);
    
    ExtensionPoint getExtensionPoint(String extensionPointId);
}
