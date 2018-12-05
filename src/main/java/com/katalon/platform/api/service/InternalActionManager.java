package com.katalon.platform.api.service;

public interface InternalActionManager {
    <T extends InternalAction> T getAction(Class<T> clazz);
}