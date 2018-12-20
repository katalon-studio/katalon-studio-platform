package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.dialogs.CommonDialogs;

public class ApplicationManager {
    private static Application instance;
    
    private static ProjectManager projectManager;
    
    private static FolderManager folderManager;
    
    private static TestCaseManager testCaseManager;
    
    private static CommonDialogs commonDialogs;
    
    private static UISynchronizeService uiSynchronizeService;

    public static void setApplication(Application application) {
        instance = application;
    }

    public static Application getInstance() {
        return instance;
    }
    
    public static ProjectManager getProjectManager() {
        return projectManager;
    }
    
    public static void setProjectManager(ProjectManager projectManagerInstance) {
        projectManager = projectManagerInstance;
    }
    
    public static FolderManager getFolderManager() {
        return folderManager;
    }
    
    public static void setFolderManager(FolderManager folderManagerInstance) {
        folderManager = folderManagerInstance;
    }
    
    public static TestCaseManager getTestCaseManager() {
        return testCaseManager;
    }
    
    public static void setTestCaseManager(TestCaseManager testCaseManagerInstance) {
        testCaseManager = testCaseManagerInstance;
    }
    
    public static CommonDialogs getCommonDialogs() {
        return commonDialogs;
    }
    
    public static void setCommonDialogs(CommonDialogs commonDialogsInstance) {
        commonDialogs = commonDialogsInstance;
    }
    
    public static UISynchronizeService getUISynchronizeService() {
        return uiSynchronizeService;
    }
    
    public static void setUISynchronizeService(UISynchronizeService uiSynchronizeServiceInstance) {
        uiSynchronizeService = uiSynchronizeServiceInstance;
    }
}
