package com.katalon.platform.api.extension.event;

public interface EventListenerInitializer {
    void registerListener(EventListener listener);
    void onInstall(String pluginId);
    void onUninstall(String pluginId);
}
