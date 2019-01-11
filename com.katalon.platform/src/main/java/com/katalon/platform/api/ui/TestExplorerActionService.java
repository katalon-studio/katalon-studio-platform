package com.katalon.platform.api.ui;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;

public interface TestExplorerActionService extends UIService {
    void refreshFolder(ProjectEntity project, FolderEntity folder) throws ResourceException;

    void selectTestCases(ProjectEntity project, List<TestCaseEntity> testCaseEntities) throws ResourceException;
}
