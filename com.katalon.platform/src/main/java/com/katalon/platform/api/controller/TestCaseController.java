package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;

public interface TestCaseController extends Controller {
    
    TestCaseEntity getTestCase(ProjectEntity project, String testCaseId) throws ResourceException;

    TestCaseEntity newTestCase(ProjectEntity project, FolderEntity parentFolder, NewDescription testCaseDescription) throws ResourceException;

    TestCaseEntity updateTestCase(ProjectEntity project, TestCaseEntity testCase, UpdateDescription updateDescription) throws ResourceException;

    TestCaseEntity updateIntegration(ProjectEntity project, TestCaseEntity testCase, Integration integration) throws ResourceException;

    String getAvailableTestCaseName(ProjectEntity project, FolderEntity parentFolder, String name) throws ResourceException;

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
