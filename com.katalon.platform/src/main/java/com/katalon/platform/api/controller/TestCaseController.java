package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * TestCaseController is a unique KS Controller to help KS plugins can manipulate {@link TestCaseEntity} in the file
 * system.
 * 
 * @see #getTestCase(ProjectEntity, String)
 * @see #newTestCase(ProjectEntity, FolderEntity, NewDescription)
 * @see #updateTestCase(ProjectEntity, TestCaseEntity, UpdateDescription)
 * @see #updateIntegration(ProjectEntity, TestCaseEntity, Integration)
 * @see #getAvailableTestCaseName(ProjectEntity, FolderEntity, String)
 * 
 * @since 1.0.4
 */
public interface TestCaseController extends Controller {

    /**
     * Returns an instance of TestCaseEntity of the given <code>project</code> by the give <code>testCaseId</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param testCaseId id of TestCaseEntity
     * @return an instance of TestCaseEntity
     * @throws ResourceException if KS could not read or access test case.
     * 
     * @since 1.0.4
     */
    TestCaseEntity getTestCase(ProjectEntity project, String testCaseId) throws ResourceException;

    /**
     * Create a new test case under <code>parentFolder</code> with the given <code>testCaseDescription</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder of test case
     * @param testCaseDescription the description of new test case
     * @return new created TestCaseEntity
     * @throws ResourceException if KS could not create test case.
     * 
     * @since 1.0.4
     */
    TestCaseEntity newTestCase(ProjectEntity project, FolderEntity parentFolder, NewDescription testCaseDescription)
            throws ResourceException;

    /**
     * Updates the given <code>testCase</code> of project by the specified <code>updateDescription</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param testCase the test case that needs to be updated
     * @param updateDescription the updated description
     * @return the updated test case
     * @throws ResourceException if KS could not modify test case.
     * 
     * @since 1.0.4
     */
    TestCaseEntity updateTestCase(ProjectEntity project, TestCaseEntity testCase, UpdateDescription updateDescription)
            throws ResourceException;

    /**
     * Updates or inserts the specified <code>integration</code> into the given <code>testCase</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param testCase the test case needs to be updated
     * @param integration the integration information
     * @return the updated test case
     * @throws ResourceException if KS could not modify test case.
     * 
     * @since 1.0.4
     */
    TestCaseEntity updateIntegration(ProjectEntity project, TestCaseEntity testCase, Integration integration)
            throws ResourceException;

    /**
     * Returns an available test case name for the given <code>name</code>
     * <p>
     * Sample of using: We want to create a folder with name <b>New Test Case</b> under root folder <i>Test Cases</i>
     * <br>
     * Case 1: There is no <b>New Test Case</b> test case under <code>Test Cases</code> then the result is <b>New
     * Test Case</b>
     * <br>
     * Case 2: <b>New Test Case</b> test case exists, but <b>New Test Case 1</b> doesn't then the result is <b>New
     * Test Case 1</b>
     * <br>
     * Case 3: <b>New Test Case</b> and <b>New Test Case 1</b> folder exist, but <b>New Test Case 2</b> doesn't then
     * the result is <b>New Test Case 2</b>
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder that needs to check name
     * @param name name of new test case. Name should not be blank or content non-English characters.
     * @return a new available name
     * @throws ResourceException thrown if name is invalid format, or KS could not read or access parent folder.
     * 
     * @since 1.0.4
     */
    String getAvailableTestCaseName(ProjectEntity project, FolderEntity parentFolder, String name)
            throws ResourceException;

    /**
     * The description information of new test case
     * 
     * @since 1.0.4
     */
    public interface NewDescription {
        String getName();

        String getDescription();

        String getComment();
    }

    /**
     * The description information of a test case that needs to be updated
     * 
     * @since 1.0.4
     *
     */
    public interface UpdateDescription {
        String getDescription();

        String getComment();
    }
}
