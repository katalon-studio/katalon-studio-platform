package com.katalon.platform.api.extension;

public interface ToolItemDescription {
    String NEW_TOOLITEM_EXTENSION_POINT_ID = "com.katalon.platform.api.extension.newToolItem";

    String toolItemId();

    String name();

    String iconUrl();

    public void handleEvent();

    default boolean isItemEnabled() {
        return true;
    }
}
