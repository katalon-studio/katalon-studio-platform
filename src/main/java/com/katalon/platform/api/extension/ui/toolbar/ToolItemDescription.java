package com.katalon.platform.api.extension.ui.toolbar;

public interface ToolItemDescription {
    String NEW_TOOLITEM_EXTENSION_POINT_ID = "com.katalon.platform.newToolItem";

    String toolItemId();

    String name();

    String iconUrl();

    public void handleEvent();

    default boolean isItemEnabled() {
        return true;
    }
}
