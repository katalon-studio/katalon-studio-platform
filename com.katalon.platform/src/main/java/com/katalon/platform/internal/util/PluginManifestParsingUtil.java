package com.katalon.platform.internal.util;

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

    private static final String ATTR_REQUIRES_UI = "requiresUI";

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
                    if (isHeadlessRuntime() && extensionManager.getExtensionPoint(extensionPointId) == null) {
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
                    if (!isExtensionPointAvailable(System.getProperty(RUNTIME_PROPERTY),
                            element.getAttribute(ATTR_REQUIRES_UI))) {
                        continue;
                    }
                    String pluginId = e.getNamespaceIdentifier();
                    String extensionPointId = element.getAttribute(ExtensionConstants.ATTR_ID);
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

    static boolean isExtensionPointAvailable(String runtime, String requiresUi) {
        return !HEADLESS_RUNTIME.equalsIgnoreCase(runtime) || !Boolean.parseBoolean(requiresUi);
    }

    private static boolean isHeadlessRuntime() {
        return HEADLESS_RUNTIME.equalsIgnoreCase(System.getProperty(RUNTIME_PROPERTY));
    }
}
