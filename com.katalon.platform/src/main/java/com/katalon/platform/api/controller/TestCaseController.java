package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.TestCaseEntity;

public interface TestCaseController {
    
    TestCaseEntity getTestCase(String testCaseId);

    TestCaseEntity newTestCase(String parentFolderId, NewDescription testCaseDescription) throws ResourceException;

    TestCaseEntity updateTestCase(String testCaseId, UpdateDescription updateDescription) throws ResourceException;

    TestCaseEntity updateIntegration(String testCaseId, Integration integration) throws ResourceException;

    String getAvailableTestCaseName(String parentFolderId, String name) throws ResourceException;

    public interface NewDescription {
        String getName();

        String getDescription();

        String getComment();
    }

    public interface UpdateDescription {
        String getDescription();

        String getComment();
    }
}
