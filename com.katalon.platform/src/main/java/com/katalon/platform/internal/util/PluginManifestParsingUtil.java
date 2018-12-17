package com.katalon.platform.internal.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.osgi.framework.Bundle;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.ExtensionConstants;
import com.katalon.platform.api.ExtensionListener;
import com.katalon.platform.api.ExtensionPoint;
import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.ExtensionImpl;
import com.katalon.platform.internal.ExtensionManagerImpl;
import com.katalon.platform.internal.ExtensionPointImpl;
import com.katalon.platform.internal.KatalonPluginImpl;

public class PluginManifestParsingUtil {

    public static Plugin parsePlugin(Bundle bundle, IExtensionRegistry extensionRegistry) {
        ExtensionManagerImpl extensionManager = (ExtensionManagerImpl) ApplicationManager.getInstance()
                .getExtensionManager();
        String symbolicName = bundle.getSymbolicName();
        KatalonPluginImpl pluginImpl = new KatalonPluginImpl(symbolicName);

        IExtension[] extensions = extensionRegistry.getExtensions(symbolicName);
        for (IExtension e : extensions) {
            if (e.getExtensionPointUniqueIdentifier().equals(ExtensionConstants.EXTENSION_ID)) {
                try {
                    String pluginId = e.getNamespaceIdentifier();
                    String extensionId = e.getConfigurationElements()[0].getAttribute(ExtensionConstants.ATTR_ID);
                    String extensionPointId = e.getConfigurationElements()[0]
                            .getAttribute(ExtensionConstants.ATTR_EXTENSION_POINT_ID);
                    Object implementationClass = e.getConfigurationElements()[0]
                            .createExecutableExtension(ExtensionConstants.ATTR_IMPLEMENTATION_CLASS);

                    Extension newExtension = new ExtensionImpl(pluginId, extensionId, extensionPointId,
                            implementationClass);

                    pluginImpl.addExtension(newExtension);

                    extensionManager.addExtension(extensionPointId, newExtension);
                } catch (InvalidRegistryObjectException | CoreException ex) {
                    ex.printStackTrace(System.err);
                }
            }

            if (e.getExtensionPointUniqueIdentifier().equals(ExtensionConstants.EXTENSION_POINT_ID)) {
                try {
                    String pluginId = e.getNamespaceIdentifier();
                    String extensionPointId = e.getConfigurationElements()[0].getAttribute(ExtensionConstants.ATTR_ID);
                    String interfaceClassName = e.getConfigurationElements()[0]
                            .getAttribute(ExtensionConstants.ATTR_INTERFACE_CLASS);

                    ExtensionListener serviceClass = null;
                    if (e.getConfigurationElements()[0].getAttribute(ExtensionConstants.ATTR_SERVICE_CLASS) != null) {
                        serviceClass = (ExtensionListener) e.getConfigurationElements()[0]
                                .createExecutableExtension(ExtensionConstants.ATTR_SERVICE_CLASS);
                    }

                    ExtensionPoint newExtensionPoint = new ExtensionPointImpl(pluginId, extensionPointId,
                            interfaceClassName, serviceClass);

                    pluginImpl.addExtensionPoint(newExtensionPoint);

                    extensionManager.addExtensionPoint(extensionPointId, newExtensionPoint);
                } catch (InvalidRegistryObjectException | CoreException ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }
        return pluginImpl;
    }
}
