package com.katalon.platform.internal.lifecycle;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.extension.PluginActivationListener;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.internal.EclipseContextService;
import com.katalon.platform.internal.event.EventConstants;

public class PluginActivationListenerService implements ExtensionListener, EventHandler {

    private Map<String, PluginActivationListener> lookup = new HashMap<>();

    @Override
    public void onPostConstruct() {
        IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
        eventBroker.subscribe("KATALON_PLUGIN/AFTER_ACTIVATION", this);
        eventBroker.subscribe("KATALON_PLUGIN/BEFORE_DEACTIVATION", this);
    }

    @Override
    public void onPreDestroy() {
        IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
        eventBroker.unsubscribe(this);
        lookup.clear();
    }

    @Override
    public void register(Extension extension) {
        if (extension.getImplementationClass() instanceof PluginActivationListener) {
            lookup.put(extension.getPluginId(),
                    (PluginActivationListener) extension.getImplementationClass());
        }
    }
    
    @Override
    public void deregister(Extension extension) {
        if (extension.getImplementationClass() instanceof PluginActivationListener
                && lookup.containsKey(extension.getPluginId())) {
            lookup.put(extension.getPluginId(),
                    (PluginActivationListener) extension.getImplementationClass());
        }
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case "KATALON_PLUGIN/AFTER_ACTIVATION": {
                Plugin plugin = (Plugin) event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                if (lookup.containsKey(plugin.getPluginId())) {
                    lookup.get(plugin.getPluginId()).afterActivation(plugin);
                }
                break;
            }
            case "KATALON_PLUGIN/BEFORE_DEACTIVATION": {
                Plugin plugin = (Plugin) event.getProperty(EventConstants.EVENT_DATA_PROPERTY_NAME);
                if (lookup.containsKey(plugin.getPluginId())) {
                    lookup.get(plugin.getPluginId()).beforeDeactivation(plugin);
                }
                break;
            }
        }
    }
}
