package com.katalon.platform.api.model;

import java.io.File;

public interface TestCaseEntity extends Entity, HasIntegration {
    String getParentFolderId();

    String getDescription();

    String getComment();

    File getScriptFile();
    
    String getTags();
}
