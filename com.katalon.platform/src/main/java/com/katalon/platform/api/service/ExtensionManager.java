package com.katalon.platform.api.service;

import java.util.Collection;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.Extension;
import com.katalon.platform.api.ExtensionPoint;

/**
 * ExtensionManager is a unique instance interface that maintains list of registered <i>Extension</i> and
 * <i>ExtensionPoint</i>.
 * <p>
 * The unique instance of ExtensionManager can access by using {@link Application#getExtensionManager()}
 * 
 * @since 1.0.4
 */
public interface ExtensionManager {

    /**
     * Returns a collection of registered Extension that points to the given <code>extensionPointId</code>.
     * Each Extension in the returned collection is desired to contribute the feature of the associated ExtensionPoint
     * provided.
     * <p>
     * This collection is immutable and cannot be changed by clients.
     * 
     * @param extensionPointId id of the ExtensionPoint.
     * @return an instance of collection of Extension. It can be empty but not null.
     * 
     * @since 1.0.4
     */
    Collection<Extension> getExtensions(String extensionPointId);

    /**
     * Returns an instance of registered ExtensionPoint associated with the given <code>extensionPointId</code>
     * 
     * @param extensionPointId id of the ExtensionPoint.
     * @return an unique instance of ExtensionPoint. It can be null it the given <code>extensionPointId</code> is not
     * matched with any ExtensionPoint in ExtensionManager.
     * 
     * @since 1.0.4
     */
    ExtensionPoint getExtensionPoint(String extensionPointId);
}
