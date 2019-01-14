package com.katalon.platform.api.model;

import java.util.List;

public interface TestSuiteEntity extends HasIntegration {
    String getParentFolderId();

    String getDescription();
    
    String getTag();
    
    List<String> getTestCaseIds();
}
