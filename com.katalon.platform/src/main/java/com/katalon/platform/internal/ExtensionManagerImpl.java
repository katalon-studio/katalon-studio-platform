package com.katalon.platform.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.ExtensionPoint;
import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.api.service.ExtensionManager;

public class ExtensionManagerImpl implements ExtensionManager {

    private Map<String, Set<Extension>> extensionCollections = new HashMap<>();

    private Map<String, ExtensionPoint> extensionPointCollections = new HashMap<>();

    @Override
    public Collection<Extension> getExtensions(String extensionPointId) {
        return extensionCollections.getOrDefault(extensionPointId, Collections.emptySet());
    }

    public void addExtension(String extensionPointId, Extension extension) {
        Set<Extension> extensions = extensionCollections.getOrDefault(extensionPointId, new LinkedHashSet<>());
        extensions.add(extension);
        extensionCollections.put(extensionPointId, extensions);
    }

    public void addExtensionPoint(String extensionPointId, ExtensionPoint extensionPoint) {
        extensionPointCollections.put(extensionPointId, extensionPoint);
    }

    public void removeExtension(Extension extension) {
        String extensionPointId = extension.getExtensionPointId();
        Set<Extension> extensions = extensionCollections.getOrDefault(extensionPointId, new LinkedHashSet<>());

        if (extensions.contains(extension)) {
            extensions.remove(extension);
        }
        extensionCollections.put(extensionPointId, extensions);
    }

    public void removeExtensionPoint(String extensionPointId) {
        extensionCollections.remove(extensionPointId);
        extensionPointCollections.remove(extensionPointId);
    }

    @Override
    public ExtensionPoint getExtensionPoint(String extensionPointId) {
        return extensionPointCollections.getOrDefault(extensionPointId, null);
    }

    public void registerExtensions(Plugin plugin) {
        plugin.getExtensions().stream().forEach(e -> {
            ExtensionPoint extensionPoint = getExtensionPoint(e.getExtensionPointId());
            if (extensionPoint != null) {
                ExtensionListener serviceClass = extensionPoint.getServiceClass();
                if (serviceClass != null) {
                    try {
                        serviceClass.register(e);
                    } catch (Exception ex) {
                        ex.printStackTrace(System.out);
                    }
                }
            }
        });
    }

    public void registerExtensionsPoint(Plugin plugin) {
        plugin.getExtensionPoints().stream().forEach(extensionPoint -> {
            ExtensionListener serviceClass = extensionPoint.getServiceClass();
            if (serviceClass != null) {
                serviceClass.onPostConstruct();
                getExtensions(extensionPoint.getPluginId()).stream().forEach(e -> {
                    serviceClass.register(e);
                });
            }
        });
    }

    public void deregisterExtensionsPoint(Plugin plugin) {
        plugin.getExtensionPoints().stream().forEach(extensionPoint -> {
            ExtensionListener serviceClass = extensionPoint.getServiceClass();
            if (serviceClass != null) {
                serviceClass.onPreDestroy();
                getExtensions(extensionPoint.getPluginId()).stream().forEach(e -> {
                    serviceClass.deregister(e);
                });
            }
        });
    }

    public void deregisterExtensions(Plugin plugin) {
        plugin.getExtensions().stream().forEach(e -> {
            ExtensionPoint extensionPoint = getExtensionPoint(e.getExtensionPointId());
            if (extensionPoint != null) {
                ExtensionListener serviceClass = extensionPoint.getServiceClass();
                if (serviceClass != null) {
                    serviceClass.deregister(e);
                }
            }
        });
    }
}
