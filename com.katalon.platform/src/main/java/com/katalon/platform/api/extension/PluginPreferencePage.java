package com.katalon.platform.api.extension;

import org.eclipse.jface.preference.PreferencePage;

/**
 * PluginPreferencePage is the interface of <code>com.katalon.platform.api.extension.pluginPreferencePage</code>
 * extension point that allows client plugins can add a Plugin Setting page under <b>Plugins</b> category in <b>Project
 * Settings</b> dialog.
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.pluginPreferencePage"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * PluginPreferencePage.
 * 
 * @since 1.0.4
 *
 */
public interface PluginPreferencePage {
    /**
     * Id of this extension point
     * 
     * @since 1.0.4
     */
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.pluginPreferencePage";

    /**
     * @return Name of the plugin page
     * @since 1.0.4
     */
    String getName();

    /**
     * @return Id of the plugin page
     * @since 1.0.4
     */
    String getPageId();

    /**
     * @return clazz that extends PreferencePage
     * @since 1.0.4
     */
    Class<? extends PreferencePage> getPreferencePageClass();
}
