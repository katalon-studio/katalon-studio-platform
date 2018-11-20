package com.katalon.platform.api.extension.ui;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.katalon.platform.internal.ApplicationServiceImpl;

public class DialogUtil {

    public static void openPreferencePage(String preferenceId) {
        IEventBroker eventBroker = ApplicationServiceImpl.get(IEventBroker.class);
        eventBroker.post("KATALON/PREFERENCES", 
                "com.kms.katalon.composer.preferences.GeneralPreferencePage/"
                + "com.kms.katalon.composer.preferences.PluginPreferencePage/"
                + preferenceId);
    }
}
