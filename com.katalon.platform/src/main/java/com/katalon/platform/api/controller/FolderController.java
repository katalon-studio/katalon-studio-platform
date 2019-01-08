package com.katalon.platform.api.controller;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;

public interface FolderController extends Controller {
    FolderEntity getFolder(ProjectEntity project, String folderId) throws ResourceException;

    FolderEntity newFolder(ProjectEntity project, FolderEntity parentFolder, String name) throws ResourceException;

    String getAvailableFolderName(ProjectEntity project, FolderEntity parentFolder, String name)
            throws ResourceException;

    List<TestCaseEntity> getChildTestCases(ProjectEntity project, FolderEntity parentFolder) throws ResourceException;
}