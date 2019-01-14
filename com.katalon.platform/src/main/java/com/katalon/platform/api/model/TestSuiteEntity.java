package com.katalon.platform.api.model;


public interface TestSuiteEntity extends HasIntegration {
    String getParentFolderId();

    String getDescription();
}
