package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.service.InternalAction;
import com.katalon.platform.api.service.InternalActionManager;

public class InternalActionManagerImpl implements InternalActionManager {

    private Map<String, InternalAction> internalActionLookup = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends InternalAction> T getAction(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return (T) internalActionLookup.getOrDefault(clazz.getName(), null);
    }

    public <T extends InternalAction> void addAction(Class<T> clazz, T action) {
        internalActionLookup.put(clazz.getName(), action);
    }
    
    public <T extends InternalAction> void removeAction(Class<T> clazz, T action) {
        String actionClassName = clazz.getName();
        if (internalActionLookup.containsKey(actionClassName)) {
            internalActionLookup.remove(actionClassName);
        }
    }
}
