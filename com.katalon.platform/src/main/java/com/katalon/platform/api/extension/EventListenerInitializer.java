package com.katalon.platform.api.extension;

import com.katalon.platform.api.event.EventListener;

public interface EventListenerInitializer {

    String EVENT_LISTENER_EXP_ID = "com.katalon.platform.api.extension.eventListener";

    void registerListener(EventListener listener);
}
