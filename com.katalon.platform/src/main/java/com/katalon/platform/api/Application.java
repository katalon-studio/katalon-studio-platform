package com.katalon.platform.api;

import com.katalon.platform.api.controller.Controller;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.preference.PluginPreference;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.api.service.ControllerManager;
import com.katalon.platform.api.service.ExtensionManager;
import com.katalon.platform.api.service.PluginManager;
import com.katalon.platform.api.service.PreferenceManager;
import com.katalon.platform.api.service.ProjectManager;
import com.katalon.platform.api.service.UIServiceManager;
import com.katalon.platform.api.ui.UIService;

/**
 * Represents for a single running KS application. Application is a central point to gather all important services of KS
 * Platform.
 * <p>
 * The unique instance of Application can access by using {@link ApplicationManager#getInstance()}
 * 
 * @see #getControllerManager()
 * @see #getExtensionManager()
 * @see #getPreferenceManager()
 * @see #getPluginManager()
 * @see #getProjectManager()
 * @see #getUIServiceManager()
 * @since 1.0.4
 */
public interface Application {

    /**
     * PluginManager is an interface that maintains list of registered {@link Plugin}.
     * 
     * @return an unique instance of PluginManager.
     * @since 1.0.4
     */
    PluginManager getPluginManager();

    /**
     * ExtensionManager is an interface that maintains list of registered {@link Extension} and {@link ExtensionPoint}.
     * 
     * @return an unique instance of ExtensionManager.
     * @since 1.0.4
     */
    ExtensionManager getExtensionManager();

    /**
     * ProjectManager is an interface that maintains the current working {@link ProjectEntity}.
     * 
     * @return an unique instance of ExtensionManager.
     * @since 1.0.4
     */
    ProjectManager getProjectManager();

    /**
     * PreferenceManager is an interface that maintains list of registered {@link PluginPreference}.
     * 
     * @return an unique instance of PreferenceManager.
     * @since 1.0.4
     */
    PreferenceManager getPreferenceManager();

    /**
     * ControllerManager is an interface that maintains list of registered {@link Controller}.
     * 
     * @return an unique instance of ControllerManager.
     * @since 1.0.4
     */
    ControllerManager getControllerManager();

    /**
     * UIServiceManager is an interface that maintains list of registered {@link UIService}.
     * 
     * @return an unique instance of UIServiceManager.
     * @since 1.0.4
     */
    UIServiceManager getUIServiceManager();
}
