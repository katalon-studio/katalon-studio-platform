package com.katalon.platform.internal.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.ExtensionPoint;
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
        String extensionPointId = extension.extensionPointId();
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
        plugin.extensions().stream().forEach(e -> {
            ExtensionPoint extensionPoint = getExtensionPoint(e.extensionPointId());
            if (extensionPoint != null) {
                ExtensionListener serviceClass = extensionPoint.serviceClass();
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
        plugin.extensionsPoint().stream().forEach(extensionPoint -> {
            ExtensionListener serviceClass = extensionPoint.serviceClass();
            if (serviceClass != null) {
                getExtensions(extensionPoint.pluginId()).stream().forEach(e -> {
                    serviceClass.register(e);
                });
            }
        });
    }

    public void deregisterExtensionsPoint(Plugin plugin) {
        plugin.extensionsPoint().stream().forEach(extensionPoint -> {
            ExtensionListener serviceClass = extensionPoint.serviceClass();
            if (serviceClass != null) {
                getExtensions(extensionPoint.pluginId()).stream().forEach(e -> {
                    serviceClass.deregister(e);
                });
            }
        });
    }

    public void deregisterExtensions(Plugin plugin) {
        plugin.extensions().stream().forEach(e -> {
            ExtensionPoint extensionPoint = getExtensionPoint(e.extensionPointId());
            ExtensionListener serviceClass = extensionPoint.serviceClass();
            if (serviceClass != null) {
                serviceClass.deregister(e);
            }
        });
    }
}
