package com.katalon.platform.api;

import com.katalon.platform.api.dialogs.CommonDialogs;
import com.katalon.platform.api.service.FolderManager;
import com.katalon.platform.api.service.ProjectManager;
import com.katalon.platform.api.service.TestCaseManager;

public class PlatformServices {

    private static ProjectManager _projectManager;
    
    private static FolderManager _folderManager;
    
    private static TestCaseManager _testCaseManager;
    
    private static CommonDialogs _commonDialogs;
    
    public static ProjectManager getProjectManager() {
        return _projectManager;
    }
    
    public synchronized static void setProjectManager(ProjectManager projectManager) {
        _projectManager = projectManager; 
    }
    
    public static FolderManager getFolderManager() {
        return _folderManager;
    }
    
    public synchronized static void setFolderManager(FolderManager folderManager) {
        _folderManager = folderManager;
    }
    
    public static TestCaseManager getTestCaseManager() {
        return _testCaseManager;
    }
    
    public synchronized static void setTestCaseManager(TestCaseManager testCaseManager) {
        _testCaseManager = testCaseManager;
    }
    
    public static CommonDialogs getCommonDialogsUtil() {
        return _commonDialogs;
    }
    
    public synchronized static void setCommonDialogsUtil(CommonDialogs commonDialogs) {
        _commonDialogs = commonDialogs;
    }
}
