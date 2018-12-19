package com.katalon.platform.internal.event;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.extension.EventListenerInitializer;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.internal.EclipseContextService;

public class EventListenerService implements ExtensionListener {

    private Map<String, EventListernerDelegate> eventListenerLookup = new HashMap<>();

    @Override
    public void register(Extension extension) {
        if (extension.getImplementationClass() instanceof EventListenerInitializer) {
            EventListenerInitializer eventListenerInitializer = (EventListenerInitializer) extension
                    .getImplementationClass();
            EventListernerDelegate delegate = new EventListernerDelegate(extension.getExtensionId());

            eventListenerInitializer.registerListener(delegate);

            IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
            eventBroker.subscribe("KATALON_EXECUTION/*", delegate.getEventHandler());
            eventBroker.subscribe("KATALON_PLUGIN/CURRENT_PROJECT_CHANGED", delegate.getEventHandler());
            
            eventListenerLookup.put(extension.extensionId(), delegate);
        }
    }

    @Override
    public void deregister(Extension extension) {
        String extensionId = extension.getExtensionId();
        if (eventListenerLookup.containsKey(extensionId)) {
        	EventListenerInitializer eventListenerInitializer = (EventListenerInitializer) extension
                    .implementationClass();
            IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
            eventBroker.unsubscribe(eventListenerLookup.get(extensionId).getEventHandler());

            eventListenerLookup.remove(extensionId);
        }
    }
}
