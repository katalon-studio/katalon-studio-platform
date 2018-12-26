package com.katalon.platform.internal.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.katalon.platform.api.event.EventListener;


@SuppressWarnings("rawtypes")
public class EventListernerDelegate implements EventListener, EventHandler {
    private final Map<Class, List<Consumer>> consumerLookup = new HashMap<>();

    private final EventHandler eventHandler = new EventHandler() {

        @Override
        public void handleEvent(Event event) {
            try {
                EventListernerDelegate.this.handleEvent(event);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    };

    public EventListernerDelegate(String pluginId) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void handleEvent(Event event) {
        List<Consumer> consumers = consumerLookup.get(Event.class);
        consumers.stream().forEach(consumer -> consumer.accept(event));
    }

    @Override
    public <T> void on(Class<T> eventType, Consumer<T> consumer) {
        List<Consumer> consumers = consumerLookup.get(eventType);
        if (consumers == null) {
            consumers = new ArrayList<>();
            consumerLookup.put(eventType, consumers);
        }
        consumers.add(consumer);
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }
}
