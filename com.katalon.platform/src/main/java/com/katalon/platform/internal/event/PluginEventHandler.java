package com.katalon.platform.internal.event;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.model.Entity;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.EclipseContextService;
import com.katalon.platform.internal.ExtensionManagerImpl;
import com.katalon.platform.internal.PluginManagerImpl;
import com.katalon.platform.internal.ProjectManagerImpl;
import com.katalon.platform.internal.util.PluginManifestParsingUtil;

public class PluginEventHandler implements EventHandler {

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case "KATALON_PLUGIN/INSTALL": {
                Object[] objects = (Object[]) event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                try {
                    installPlugin((BundleContext) objects[0], (String) objects[1]);
                } catch (BundleException e) {
                    e.printStackTrace(System.err);
                }
                break;
            }
            case "KATALON_PLUGIN/UNINSTALL": {
                Object[] objects = (Object[]) event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                try {
                    uninstallPlugin((BundleContext) objects[0], (String) objects[1]);
                } catch (BundleException e) {
                    e.printStackTrace(System.err);
                }
                break;
            }
            case "KATALON_PLUGIN/CURRENT_PROJECT_CHANGED": {
            	Object object = event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);	
                updateCurrentProject((Entity) object);
                break;
            }
        }
    }
    
    public void updateCurrentProject(Entity project){
    	ProjectManagerImpl projectManager = (ProjectManagerImpl) ApplicationManager.getInstance().getProjectManager();
    	projectManager.setCurrentProject(project);
    }
    
    public Bundle installPlugin(BundleContext bundleContext, String location)
            throws BundleException {
        Bundle bundle = bundleContext.installBundle(location);
        bundle.start();

        Plugin userPlugin = PluginManifestParsingUtil.parsePlugin(Platform.getBundle(bundle.getSymbolicName()),
                Platform.getExtensionRegistry());

        PluginManagerImpl pluginManager = (PluginManagerImpl) ApplicationManager.getInstance().getPluginManager();
        pluginManager.addPlugin(userPlugin);

        ExtensionManagerImpl extensionManager = (ExtensionManagerImpl) ApplicationManager.getInstance()
                .getExtensionManager();

        // Register all extensions of this plugin to other plugins
        extensionManager.registerExtensions(userPlugin);

        // Register all extensions of other plugins to this plugin
        extensionManager.registerExtensionsPoint(userPlugin);
        
        IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
        eventBroker.send("KATALON_PLUGIN/AFTER_ACTIVATION", userPlugin);

        return bundle;
    }

    public void uninstallPlugin(BundleContext context, String location)
            throws BundleException {
        Bundle bundle = context.getBundle(location);
        if (bundle == null) {
            return;
        }

        String bundleName = bundle.getSymbolicName();

        Application application = ApplicationManager.getInstance();
        Plugin userPlugin = application.getPluginManager().getPlugin(bundleName);
        
        IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
        eventBroker.send("KATALON_PLUGIN/BEFORE_DEACTIVATION", userPlugin);

        ExtensionManagerImpl extensionManager = (ExtensionManagerImpl) application.getExtensionManager();

        // De-register all extensions that is contributing to this plugin.
        extensionManager.deregisterExtensionsPoint(userPlugin);
        userPlugin.getExtensionPoints().stream().forEach(p -> extensionManager.removeExtensionPoint(p.getExtensionPointId()));

        // De-register all extensions of this plugin from other plugins.
        extensionManager.deregisterExtensions(userPlugin);
        userPlugin.getExtensions().forEach(e -> extensionManager.removeExtension(e));

        PluginManagerImpl pluginManager = (PluginManagerImpl) application.getPluginManager();
        pluginManager.removePlugin(userPlugin);

        bundle.stop();
        bundle.uninstall();
    }
}
