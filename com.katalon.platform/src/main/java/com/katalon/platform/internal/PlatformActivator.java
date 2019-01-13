package com.katalon.platform.internal;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.PluginInstaller;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.event.PluginEventHandler;
import com.katalon.platform.internal.util.PluginManifestParsingUtil;

public class PlatformActivator extends org.eclipse.core.runtime.Plugin {

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);

        ApplicationManager.setApplication(new ApplicationImpl());

        // Look up IEclipseContext
        IEclipseContext eclipseContext = EclipseContextFactory.getServiceContext(context);
        EclipseContextService.lookupPlatformContext(eclipseContext);

        IEventBroker eventBroker = eclipseContext.get(IEventBroker.class);
        PluginEventHandler eventHandler = new PluginEventHandler();
        eventBroker.subscribe("KATALON_PLUGIN/*", eventHandler);

        context.registerService(PluginInstaller.class, eventHandler, null);

        context.addServiceListener(new ServiceListener() {

            @Override
            public void serviceChanged(ServiceEvent event) {
                if (event.getServiceReference() != null
                        && event.getServiceReference().getProperty("objectClass") instanceof String[]) {
                    String[] objectClasses = (String[]) event.getServiceReference().getProperty("objectClass");
                    if (objectClasses.length == 1 && IEclipseContext.class.getName().equals(objectClasses[0])) {
                        context.removeServiceListener(this);

                        // Look up IEclipseContext
                        IEclipseContext workbenchContext = context
                                .getService(context.getServiceReference(IEclipseContext.class));
                        EclipseContextService.lookupWorkbenchContext(workbenchContext);
                    }
                }
            }
        });

        boostrapPlatform(context);
    }

    private void boostrapPlatform(BundleContext bundleContext) throws BundleException {

        Bundle bundle = bundleContext.getBundle();

        Plugin platformPlugin = PluginManifestParsingUtil.parsePlugin(bundle, Platform.getExtensionRegistry());

        PluginManagerImpl pluginManager = (PluginManagerImpl) ApplicationManager.getInstance().getPluginManager();
        pluginManager.addPlugin(platformPlugin);

        ExtensionManagerImpl extensionManager = (ExtensionManagerImpl) ApplicationManager.getInstance()
                .getExtensionManager();

        // Register all extensions of this plugin to other plugins
        extensionManager.registerExtensions(platformPlugin);

        // Register all extensions of other plugins to this plugin
        extensionManager.registerExtensionsPoint(platformPlugin);
    }
}
