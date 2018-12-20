package com.katalon.platform.api.model;

import java.io.InputStream;

public interface TestCase extends Entity, HasIntegration {
    
    String getDescription();
    
    String getComment();
    
    InputStream getScriptContent();
}
