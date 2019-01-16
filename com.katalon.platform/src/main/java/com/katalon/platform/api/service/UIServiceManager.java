package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.exception.PlatformRuntimeException;
import com.katalon.platform.api.ui.DialogActionService;
import com.katalon.platform.api.ui.TestExplorerActionService;
import com.katalon.platform.api.ui.UIService;
import com.katalon.platform.api.ui.UISynchronizeService;

/**
 * UIServiceManager is an interface that maintains list of <i>UIService</i>.
 * <p>
 * The unique instance of UIServiceManager can access by using {@link Application#getUIServiceManager()}
 * 
 * @since 1.0.4
 */
public interface UIServiceManager {
    /**
     * Returns an instance of UIService that is provided by KS.
     * 
     * @param <T> the type of UIService
     * @param clazz an interface class that extends UIService
     * @see DialogActionService
     * @see TestExplorerActionService
     * @see UISynchronizeService
     * @throws PlatformRuntimeException if UIServiceManager doesn't contain the instance UIService that matches with
     * <code>clazz</code> parameter.
     * 
     * @return an instance of UIService that is a implementation of the <code>clazz</code>
     * @since 1.0.3
     */
    <T extends UIService> T getService(Class<T> clazz) throws PlatformRuntimeException;
}
