package com.katalon.platform.api.model;

import java.io.File;

public interface TestSuiteEntity extends HasIntegration {

    String getDescription();

    File getScriptFile();
}
