package com.katalon.platform.api.extension;

import com.katalon.platform.api.Plugin;

/**
 * PluginActivationListener is the interface of <code>com.katalon.platform.api.extension.pluginActivationListener</code>
 * extension point that allows client plugins can listen to the activation and de-activation events of the plugin
 * itself.
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.pluginActivationListener"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements PluginActivationListener.
 * <p>
 * This extension is recommended for client plugins to setup and clean their plugin environments.
 * 
 * @since 1.0.4
 *
 */
public interface PluginActivationListener {

    /**
     * Id of this extension point
     */
    String PLUGIN_ACTIVATION_LISTENER_EXTENSION_POINT_ID = "com.katalon.platform.api.extension.pluginActivationListener";

    /**
     * This method will be invoked after a plugin is activated.
     * 
     * @param plugin the installed plugin
     * @since 1.0.4
     */
    default void afterActivation(Plugin plugin) {

    }

    /**
     * This method will be invoked before a plugin is deactivated.
     * 
     * @param plugin the installed plugin
     * @since 1.0.4
     */
    default void beforeDeactivation(Plugin plugin) {

    }
}
