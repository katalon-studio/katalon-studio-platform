package com.katalon.platform.api;

/**
 * Represent for a correct Extension of a installed Plugin.
 * <p>
 * An extension is declared in <code>src/main/resources/plugin.xml</code> file, with this sample format:
 * 
 * <pre>
 * {@code
 * <extension
 *          point="com.katalon.platform.extensions">
 *      <point
 *               id="<extensions_id>"
 *               extensionPointId="<extensions_point_id>"
 *               implementationClass="<ImplementationClassName>">
 *      </point>
 * </extension>
 * }
 * </pre>
 *
 * @since 1.0.4
 */
public interface Extension {
    /**
     * Id of the plugin that owns the extension
     * 
     * @return plugin id of the extension
     * 
     * @since 1.0.4
     */
    String getPluginId();

    /**
     * Id of the extension
     * 
     * @return id of the extension
     * 
     * @since 1.0.4
     */
    String getExtensionId();

    /**
     * Id of the extension point
     * 
     * @return id of the extension point
     * 
     * @since 1.0.4
     */
    String getExtensionPointId();

    /**
     * An instance of implementation class that implements the interface that was declared in extension point.
     * <p>
     * The instance is automatically initialized by KS after the plugin that owns the extension loaded.
     * <p>
     * Clients should NOT override default constructor of the implementation class.
     * 
     * @return an implementation of the interface class that was declared in extension point
     * 
     * @since 1.0.4
     */
    Object getImplementationClass();
}
