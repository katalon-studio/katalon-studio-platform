package com.katalon.platform.api.service;

import java.util.List;

import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;

public interface FolderManager {

    FolderEntity getTestCaseRoot(ProjectEntity project) throws Exception;

    List<TestCaseEntity> getChildTestCases(FolderEntity folder) throws Exception;
}
