package com.katalon.platform.api.extension.event.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.event.EventListenerInitializer;
import com.katalon.platform.internal.ApplicationServiceImpl;

public class EventListenerService implements ExtensionListener {

    private Map<String, EventListernerDelegate> eventListenerLookup = new HashMap<>();

    @Override
    public void register(Extension extension) {
        if (extension.implementationClass() instanceof EventListenerInitializer) {
            EventListenerInitializer eventListenerInitializer = (EventListenerInitializer) extension
                    .implementationClass();
            EventListernerDelegate delegate = new EventListernerDelegate(extension.extensionId());

            eventListenerInitializer.registerListener(delegate);

            IEventBroker eventBroker = ApplicationServiceImpl.get(IEventBroker.class);
            eventBroker.subscribe("KATALON_EXECUTION/*", delegate.getEventHandler());

            eventListenerLookup.put(extension.extensionId(), delegate);
        }
    }

    @Override
    public void deregister(Extension extension) {
        String extensionId = extension.extensionId();
        if (eventListenerLookup.containsKey(extensionId)) {
            IEventBroker eventBroker = ApplicationServiceImpl.get(IEventBroker.class);
            eventBroker.unsubscribe(eventListenerLookup.get(extensionId));
        }
    }
}
