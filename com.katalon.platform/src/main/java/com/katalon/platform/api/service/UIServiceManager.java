package com.katalon.platform.api.service;

import com.katalon.platform.api.exception.PlatformRuntimeException;
import com.katalon.platform.api.ui.DialogActionService;
import com.katalon.platform.api.ui.TestExplorerActionService;
import com.katalon.platform.api.ui.UIService;
import com.katalon.platform.api.ui.UISynchronizeService;

public interface UIServiceManager {
    /**
     * Returns a instance of UIService that is provided by KS.
     * 
     * @param clazz an interface class that extends UIService
     * @see DialogActionService
     * @see TestExplorerActionService
     * @see UISynchronizeService
     * @throws PlatformRuntimeException if UIServiceManager doesn't contain the instance UIService that matches with
     * <code>clazz</code> parameter.
     * @since 1.0.3
     */
    <T extends UIService> T getService(Class<T> clazz) throws PlatformRuntimeException;
}
