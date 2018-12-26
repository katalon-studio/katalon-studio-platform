package com.katalon.platform.api.util;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.katalon.platform.internal.EclipseContextService;

public final class DialogUtil {
    
    private DialogUtil() {
        //Prevent to initialize
    }

    public static void openPreferencePage(String preferenceId) {
        IEventBroker eventBroker = EclipseContextService.getPlatformService(IEventBroker.class);
        eventBroker.post("KATALON/PREFERENCES",
                "com.kms.katalon.composer.preferences.GeneralPreferencePage/"
                + "com.kms.katalon.composer.preferences.PluginPreferencePage/" + preferenceId);
    }
}
