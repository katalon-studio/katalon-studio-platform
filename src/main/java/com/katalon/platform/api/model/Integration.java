package com.katalon.platform.api.model;

import java.util.Map;

public interface Integration {

    String getProductName();
    
    Map<String, String> getProperties();
    
    IntegrationType getType();
}
