package com.katalon.platform.api;

import java.util.Collection;

import com.katalon.platform.api.extension.PluginActivationListener;
import com.katalon.platform.api.service.PluginManager;

/**
 * Represents a correct installed Plugin of KS.
 * <p>
 * We can retrieve an installed Plugin by using {@link PluginManager#getPlugin(String)}.
 * <p>
 * A plugin can listen to activation/deactivation event of itself by using extension {@link PluginActivationListener}
 * 
 * @see #getPluginId()
 * @see #getExtensions()
 * @see #getExtensionPoints()
 * 
 * @since 1.0.4
 */
public interface Plugin {
    /**
     * Unique id of the plugin.
     * <p>
     * Id of a plugin usually is defined in pom.xml of a plugin as Maven project. It is value of
     * <code>{@code <Bundle-SymbolicName>}</code>.</br>
     * Plugin id usually comes with this format:
     * <code>artifactId.projectId</code>
     * 
     * @return unique id of the plugin
     * @since 1.0.4
     */
    String getPluginId();

    /**
     * Returns a collection of registered Extension of the plugin</code>.
     * Each extension in the returned collection is desired to contribute the feature of the associated extension point
     * provided.
     * <p>
     * This collection is immutable and cannot be changed by clients.
     * 
     * @return an instance of collection of Extension. It can be empty but not null.
     * 
     * @since 1.0.4
     */
    Collection<Extension> getExtensions();

    /**
     * Returns a collection of registered ExtensionPoint of the plugin</code>.
     * <p>
     * This collection is immutable and cannot be changed by clients.
     * 
     * @return an instance of collection of ExtensionPoint. It can be empty but not null.
     * 
     * @since 1.0.4
     */
    Collection<ExtensionPoint> getExtensionPoints();
}
