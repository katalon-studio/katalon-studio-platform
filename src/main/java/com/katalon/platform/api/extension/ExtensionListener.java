package com.katalon.platform.api.extension;

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
