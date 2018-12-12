package com.katalon.platform.api.extension.event.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.event.EventListenerInitializer;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.EclipseContextService;

public class EventListenerService implements ExtensionListener {

    private Map<String, EventListernerDelegate> eventListenerLookup = new HashMap<>();

    @Override
    public void register(Extension extension) {
        if (extension.implementationClass() instanceof EventListenerInitializer) {
            EventListenerInitializer eventListenerInitializer = (EventListenerInitializer) extension
                    .implementationClass();
            EventListernerDelegate delegate = new EventListernerDelegate(extension.extensionId());

            eventListenerInitializer.registerListener(delegate);

            IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
            eventBroker.subscribe("KATALON_EXECUTION/*", delegate.getEventHandler());
            
            eventListenerInitializer.onInstall(extension.pluginId());         
            
            eventListenerLookup.put(extension.extensionId(), delegate);
        }
    }

    @Override
    public void deregister(Extension extension) {
        String extensionId = extension.extensionId();
        if (eventListenerLookup.containsKey(extensionId)) {
        	EventListenerInitializer eventListenerInitializer = (EventListenerInitializer) extension
                    .implementationClass();
            IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
            eventBroker.unsubscribe(eventListenerLookup.get(extensionId).getEventHandler());
            
            eventListenerInitializer.onUninstall(extension.pluginId());
            
            eventListenerLookup.remove(extensionId);
        }
    }
}
