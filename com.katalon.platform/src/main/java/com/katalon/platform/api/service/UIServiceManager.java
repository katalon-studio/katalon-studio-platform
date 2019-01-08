package com.katalon.platform.api.service;

import com.katalon.platform.api.ui.UIService;

public interface UIServiceManager {
    <T extends UIService> T getService(Class<T> clazz); 
}
