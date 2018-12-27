package com.katalon.platform.api.model;

import java.io.File;

public interface TestCaseEntity extends Entity, HasIntegration {

    String getDescription();

    String getComment();

    File getScriptFile();
}
