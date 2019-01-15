package com.katalon.platform.api;

import com.katalon.platform.api.lifecycle.ExtensionListener;

/**
 * Represent for a correct ExtensionPoint of a installed Plugin.
 * <p>
 * An extension is declared in <code>src/main/resources/plugin.xml</code> file, with this sample format:
 * 
 * <pre>
 * {@code
 * <extension
 *          point="com.katalon.platform.extension_point">
 *      <point
 *               id="<extension_point_id>"
 *               interfaceClass="<InterfaceClassName>"
 *               serviceClass="<SerciceClassName>" (optional)
 *      </point>
 * </extension>
 * 
 * 
 * }
 * </pre>
 *
 * @since 1.0.4
 */
public interface ExtensionPoint {
    /**
     * Id of the plugin that owns the extension point.
     * 
     * @return plugin id of the extension point
     * 
     * @since 1.0.4
     */
    String getPluginId();

    /**
     * Id of the extension point.
     * 
     * @return id of the extension point
     * 
     * @since 1.0.4
     */
    String getExtensionPointId();

    /**
     * Name of the interface class that describes all requirements of implementation should provide to extend the
     * described features.
     * 
     * @return name of the interface class
     */
    String getInterfaceClassName();

    /**
     * ExtensionListener is a service class that can listen to the registration/de-registration Extension events of the current
     * ExtensionPoint.
     * 
     * @return instance of ExtensionListener. It can be null.
     * 
     * @see ExtensionListener
     */
    ExtensionListener getServiceClass();
}
