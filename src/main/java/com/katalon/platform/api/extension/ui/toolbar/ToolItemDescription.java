package com.katalon.platform.api.extension.ui.toolbar;

public interface ToolItemDescription {

    String groupId();

    String toolItemId();

    String name();

    default String tooltip() {
        return "";
    }

    String iconUrl();

    public void handleEvent();

    default boolean isItemEnabled() {
        return true;
    }

    default boolean isItemVisible() {
        return true;
    }
}
