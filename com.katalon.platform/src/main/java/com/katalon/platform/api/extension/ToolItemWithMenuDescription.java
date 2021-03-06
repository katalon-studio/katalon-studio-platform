package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * ToolItemWithMenuDescription is the interface of <code>com.katalon.platform.api.extension.newToolItem</code>
 * extension point that allows client plugins can put a dropdown tool item on KS main toolbar.
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.newDropdownToolItem"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements ToolItemWithMenuDescription.
 * 
 * @since 1.0.4
 *
 */
public interface ToolItemWithMenuDescription {
    /**
     * Id of the extension point
     */
    String NEW_TOOLITEM_WITH_MENU_EXTENSION_POINT_ID = "com.katalon.platform.api.extension.newDropdownToolItem";

    /**
     * @return Id of the extension tool item
     */
    String toolItemId();

    String name();

    /**
     * The platform URI point to the icon with this format:
     * {@code platform://<plugin_id>/<icon_path>}.
     * <p>
     * For example: {@code platform://com.example.my-plugin-id/icons/sample_32x24.png} then:
     * <br><i>com.example.my-plugin-id</i> is the plugin id
     * <br><i>icons/sample_32x24.png</i> is the path to icon under src/main/resources/icons folder
     * <p>
     * The icon is recommend size of 32x24. If the exact size isn't provided, this scaling can cause the icon to lose
     * detail or look fuzzy.<br>
     * Optional: We also recommend to put 2x, 3x, and large scale images with suffix {@literal @2x, @3x, @4x} likes:
     * {@literal sample_32x24@2x.png, sample_32x24@3x.png, sample_32x24@4x.png} to display better on high resolution
     * display.
     * <p>
     * Icons are recommended to be put in {@code src/main/resources/icons/} folder
     * 
     * @return the platform URI point to the icon
     * @since 1.0.4
     */
    String iconUrl();

    /**
     * Returns a menu that will be shows when use click on arrow of the tool item.
     * 
     * @param parent parent control of the menu
     * @return a menu
     */
    Menu getMenu(Control parent);

    /**
     * Handle the selection event on the item
     * 
     * @since 1.0.4
     */
    default void defaultEventHandler() {

    }

    /**
     * 
     * @return true if icon is enabled. Otherwise, false
     * @since 1.0.4
     */
    default boolean isItemEnabled() {
        return true;
    }
}
