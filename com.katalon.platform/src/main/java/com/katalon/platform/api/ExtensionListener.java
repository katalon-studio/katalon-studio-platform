package com.katalon.platform.api;

public interface ExtensionListener {
    
    default void onPostConstruct() {
        
    }
    
    default void onPreDestroy() {
        
    }

    default void register(Extension extension) {
        
    }
    
    default void deregister(Extension extension) {
        
    }
}
