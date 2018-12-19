package com.katalon.platform.api.extension;

import com.katalon.platform.api.Plugin;

public interface PluginActivationListener {

    String PLUGIN_ACTIVATION_LISTENER_EXTENSION_POINT_ID = "com.katalon.platform.api.extension.pluginActivationListener";

    default void afterActivation(Plugin plugin) {

    }

    default void beforeDeactivation(Plugin plugin) {

    }
}
