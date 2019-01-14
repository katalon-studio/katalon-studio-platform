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
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.api.service.ControllerManager;
import com.katalon.platform.api.service.UIServiceManager;
import com.katalon.platform.internal.ApplicationImpl;
import com.katalon.platform.internal.EclipseContextService;
import com.katalon.platform.internal.ExtensionManagerImpl;
import com.katalon.platform.internal.PluginManagerImpl;
import com.katalon.platform.internal.ProjectManagerImpl;
import com.katalon.platform.internal.util.PluginManifestParsingUtil;
import com.katalon.platfprm.internal.api.PluginInstaller;

public class PluginEventHandler implements EventHandler, PluginInstaller {

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case "KATALON_PLUGIN/CURRENT_PROJECT_CHANGED": {
                Object object = event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                updateCurrentProject((ProjectEntity) object);
                break;
            }
            case "KATALON_PLUGIN/CONTROLLER_MANAGER_ADDED": {
                Object object = event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                if (object instanceof ControllerManager) {
                    ApplicationImpl application = (ApplicationImpl) ApplicationManager.getInstance();
                    application.setControllerManager((ControllerManager) object);
                }
                break;
            }
            case "KATALON_PLUGIN/UISERVICE_MANAGER_ADDED": {
                Object object = event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                if (object instanceof UIServiceManager) {
                    ApplicationImpl application = (ApplicationImpl) ApplicationManager.getInstance();
                    application.setUIServiceManager((UIServiceManager) object);
                }
                break;
            }
        }
    }

    public void updateCurrentProject(ProjectEntity project) {
        ProjectManagerImpl projectManager = (ProjectManagerImpl) ApplicationManager.getInstance().getProjectManager();
        projectManager.setCurrentProject(project);
    }

    @Override
    public Bundle installPlugin(BundleContext bundleContext, String location) throws BundleException {
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

    public Bundle uninstallPlugin(BundleContext context, String location) throws BundleException {
        Bundle bundle = context.getBundle(location);
        if (bundle == null) {
            return null;
        }

        String bundleName = bundle.getSymbolicName();

        Application application = ApplicationManager.getInstance();
        Plugin userPlugin = application.getPluginManager().getPlugin(bundleName);
        if (userPlugin == null) {
            return null;
        }

        IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
        eventBroker.send("KATALON_PLUGIN/BEFORE_DEACTIVATION", userPlugin);

        ExtensionManagerImpl extensionManager = (ExtensionManagerImpl) application.getExtensionManager();

        // De-register all extensions that is contributing to this plugin.
        extensionManager.deregisterExtensionsPoint(userPlugin);
        userPlugin.getExtensionPoints()
                .stream()
                .forEach(p -> extensionManager.removeExtensionPoint(p.getExtensionPointId()));

        // De-register all extensions of this plugin from other plugins.
        extensionManager.deregisterExtensions(userPlugin);
        userPlugin.getExtensions().forEach(e -> extensionManager.removeExtension(e));

        PluginManagerImpl pluginManager = (PluginManagerImpl) application.getPluginManager();
        pluginManager.removePlugin(userPlugin);

        bundle.stop();
        bundle.uninstall();
        return bundle;
    }
}
