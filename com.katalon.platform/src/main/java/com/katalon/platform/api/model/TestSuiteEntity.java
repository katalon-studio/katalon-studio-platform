package com.katalon.platform.api.model;


public interface TestSuiteEntity extends HasIntegration, Entity {
    String getParentFolderId();

    String getDescription();
}
