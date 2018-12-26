package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public interface ToolItemWithMenuDescription {
    String NEW_TOOLITEM_WITH_MENU_EXTENSION_POINT_ID = "com.katalon.platform.api.extension.newDropdownToolItem";

    String toolItemId();

    String name();

    String iconUrl();

    Menu getMenu(Control parent);

    default void defaultEventHandler() {

    }

    default boolean isItemEnabled() {
        return true;
    }
}
