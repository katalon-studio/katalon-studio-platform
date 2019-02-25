package com.katalon.platform.api.extension;

import java.util.List;

import com.katalon.platform.api.controller.FolderController;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;
import com.katalon.platform.api.model.TestSuiteEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * DynamicQueryingTestSuiteDescription is the interface of
 * <code>com.katalon.platform.api.extension.dynamicQueryingTestSuiteDescription</code>
 * extension point that allows client plugins can add custom query test case method in the dynamic querying test suite.
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.dynamicQueryingTestSuiteDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * DynamicQueryingTestSuiteDescription.
 * 
 * @since 1.0.7
 *
 */
public interface DynamicQueryingTestSuiteDescription {

    String EXTENSION_ID = "com.katalon.platform.api.extension.dynamicQueryingTestSuiteDescription";

    /**
     * @return name of the query type
     * @since 1.0.7
     */
    String getQueryingType();

    /**
     * Returns the list of TestCaseEntity that will be scheduled to run sequentially of the given
     * <code>testSuite</code>.
     * This extension only works on the dynamic execution test suite, not the normal test suite.
     * <p>
     * List test case folders and list test cases can be retrieve by using:
     * {@link FolderController#getChildFolders(ProjectEntity, com.katalon.platform.api.model.FolderEntity)} and
     * {@link FolderController#getChildTestCases(ProjectEntity, com.katalon.platform.api.model.FolderEntity)} in FolderController
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}
     * @param testSuite the dynamic filtering test suite
     * @param fullSearchText the full search text
     * @return list of filtered TestCaseEntity.
     * @throws ResourceException If the plugin could not access project resources
     * 
     * @since 1.0.7
     */
    List<TestCaseEntity> query(ProjectEntity project, TestSuiteEntity testSuite, String fullSearchText)
            throws ResourceException;
}
