package com.katalon.platform.api.extension;

import com.katalon.platform.api.ui.view.TestCaseIntegrationView;

public interface TestCaseIntegrationViewDescription {
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testCaseIntegrationView";

    String getName();

    Class<? extends TestCaseIntegrationView> getTestCaseIntegrationView();
}