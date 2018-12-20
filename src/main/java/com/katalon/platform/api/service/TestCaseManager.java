package com.katalon.platform.api.service;

import com.katalon.platform.api.PlatformException;
import com.katalon.platform.api.model.Folder;
import com.katalon.platform.api.model.TestCase;

public interface TestCaseManager {

    TestCase newTestCase(TestCase testCase) throws PlatformException;
    
    String getAvailableTestCaseName(Folder folder, String name) throws PlatformException;
}
