package com.katalon.platform.api.ui;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * TestExplorerActionService is a unique KS UIService to help KS plugins can use KS Test Explorers built-in functions.
 * 
 * @see #refreshFolder(ProjectEntity, FolderEntity)
 * @see #selectTestCases(ProjectEntity, List)
 * 
 * @since 1.0.4
 */
public interface TestExplorerActionService extends UIService {
    /**
     * Refreshes the given <code>folder</code> of the working project on Test Explorers.
     * <p>
     * <b>Refresh</b> is an asynchronized action that actually updates the label of the folder and also updates folder
     * children labels.
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}
     * @param folder the folder that needs to refresh
     * @throws ResourceException thrown if KS could not access folder or the fresh action got wrong.
     * 
     * @since 1.0.4
     */
    void refreshFolder(ProjectEntity project, FolderEntity folder) throws ResourceException;

    /**
     * Selects the given <code>testCaseEntities</code> of the working project on Test Explorers.
     * All the test cases will be marked as selected but not opened.
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}
     * 
     * @param testCaseEntities the list of test cases that needs to be selected.
     * 
     * @throws ResourceException thrown if KS could not access test cases.
     * 
     * @since 1.0.4
     */
    void selectTestCases(ProjectEntity project, List<TestCaseEntity> testCaseEntities) throws ResourceException;
}
