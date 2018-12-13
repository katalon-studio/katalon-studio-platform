package com.katalon.platform.api.extension.event;

import java.util.function.Consumer;

public interface EventListener {
    <T> void on(Class<T> eventType, Consumer<T> consumer);
}
