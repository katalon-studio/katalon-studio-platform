package com.katalon.platform.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.osgi.framework.Bundle;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.ExtensionPoint;
import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.lifecycle.ExtensionConstants;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.ExtensionImpl;
import com.katalon.platform.internal.ExtensionManagerImpl;
import com.katalon.platform.internal.ExtensionPointImpl;
import com.katalon.platform.internal.KatalonPluginImpl;

public class PluginManifestParsingUtil {

    static final String RUNTIME_PROPERTY = "com.katalon.platform.runtime";

    static final String HEADLESS_RUNTIME = "headless";

    private static final String HEADLESS_UI_EXTENSION_POINTS_RESOURCE = "/headless-ui-extension-points.properties";

    private static final Set<String> HEADLESS_UI_EXTENSION_POINTS = loadHeadlessUiExtensionPoints();

    public static Plugin parsePlugin(Bundle bundle, IExtensionRegistry extensionRegistry) {
        ExtensionManagerImpl extensionManager = (ExtensionManagerImpl) ApplicationManager.getInstance()
                .getExtensionManager();
        String symbolicName = bundle.getSymbolicName();
        KatalonPluginImpl pluginImpl = new KatalonPluginImpl(symbolicName);

        IExtension[] extensions = extensionRegistry.getExtensions(symbolicName);
        for (IExtension e : extensions) {
            if (e.getExtensionPointUniqueIdentifier().equals(ExtensionConstants.EXTENSION_ID)) {
                try {
                    IConfigurationElement element = e.getConfigurationElements()[0];
                    String pluginId = e.getNamespaceIdentifier();
                    String extensionId = element.getAttribute(ExtensionConstants.ATTR_ID);
                    String extensionPointId = element.getAttribute(ExtensionConstants.ATTR_EXTENSION_POINT_ID);
                    if (!isExtensionPointAvailable(System.getProperty(RUNTIME_PROPERTY), extensionPointId)) {
                        continue;
                    }
                    Object implementationClass = element
                            .createExecutableExtension(ExtensionConstants.ATTR_IMPLEMENTATION_CLASS);

                    Extension newExtension = new ExtensionImpl(pluginId, extensionId, extensionPointId,
                            implementationClass);

                    pluginImpl.addExtension(newExtension);

                    extensionManager.addExtension(extensionPointId, newExtension);
                } catch (InvalidRegistryObjectException | CoreException ex) {
                    ex.printStackTrace(System.out);
                }
            }

            if (e.getExtensionPointUniqueIdentifier().equals(ExtensionConstants.EXTENSION_POINT_ID)) {
                try {
                    IConfigurationElement element = e.getConfigurationElements()[0];
                    String extensionPointId = element.getAttribute(ExtensionConstants.ATTR_ID);
                    if (!isExtensionPointAvailable(System.getProperty(RUNTIME_PROPERTY), extensionPointId)) {
                        continue;
                    }
                    String pluginId = e.getNamespaceIdentifier();
                    String interfaceClassName = element.getAttribute(ExtensionConstants.ATTR_INTERFACE_CLASS);

                    ExtensionListener serviceClass = null;
                    if (element.getAttribute(ExtensionConstants.ATTR_SERVICE_CLASS) != null) {
                        serviceClass = (ExtensionListener) element
                                .createExecutableExtension(ExtensionConstants.ATTR_SERVICE_CLASS);
                    }

                    ExtensionPoint newExtensionPoint = new ExtensionPointImpl(pluginId, extensionPointId,
                            interfaceClassName, serviceClass);

                    pluginImpl.addExtensionPoint(newExtensionPoint);

                    extensionManager.addExtensionPoint(extensionPointId, newExtensionPoint);
                } catch (InvalidRegistryObjectException | CoreException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }
        return pluginImpl;
    }

    static boolean isExtensionPointAvailable(String runtime, String extensionPointId) {
        return !HEADLESS_RUNTIME.equalsIgnoreCase(runtime) || !HEADLESS_UI_EXTENSION_POINTS.contains(extensionPointId);
    }

    private static Set<String> loadHeadlessUiExtensionPoints() {
        Properties properties = new Properties();
        try (InputStream input = PluginManifestParsingUtil.class
                .getResourceAsStream(HEADLESS_UI_EXTENSION_POINTS_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Missing platform resource " + HEADLESS_UI_EXTENSION_POINTS_RESOURCE);
            }
            properties.load(input);
        } catch (IOException error) {
            throw new IllegalStateException(
                    "Cannot load platform resource " + HEADLESS_UI_EXTENSION_POINTS_RESOURCE, error);
        }
        for (String extensionPointId : properties.stringPropertyNames()) {
            if (!Boolean.parseBoolean(properties.getProperty(extensionPointId))) {
                throw new IllegalStateException(
                        "Invalid headless UI extension point declaration: " + extensionPointId);
            }
        }
        return Collections.unmodifiableSet(properties.stringPropertyNames());
    }
}
