package com.katalon.platform.api.service;

import java.util.List;

import com.katalon.platform.api.model.Folder;
import com.katalon.platform.api.model.Project;
import com.katalon.platform.api.model.TestCase;

public interface FolderManager {
    
    Folder getTestCaseRoot(Project project) throws Exception;
    
    List<TestCase> getChildTestCases(Folder folder) throws Exception;
}
