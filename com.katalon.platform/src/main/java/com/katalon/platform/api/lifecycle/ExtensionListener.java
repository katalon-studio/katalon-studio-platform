package com.katalon.platform.api.lifecycle;

import com.katalon.platform.api.Extension;

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
