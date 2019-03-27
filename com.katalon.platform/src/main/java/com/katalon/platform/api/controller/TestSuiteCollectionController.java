package com.katalon.platform.api.controller;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestSuiteCollectionEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * TestCaseController is a unique KS Controller to help KS plugins can manipulate {@link TestSuiteCollectionEntity} in the file
 * system.
 * 
 * @see #getAllTestSuiteCollectionsInProject(ProjectEntity project)
 * @see #getTestSuiteCollection(ProjectEntity project, String testSuiteCollectionId)
 * @see #addTestSuiteToTestSuiteCollection(ProjectEntity project, String testSuiteCollectionId, String testSuiteId)
 * 
 * @since 1.0.10
 */
public interface TestSuiteCollectionController extends Controller {
	
	/**
	 * Return all instances of TestSuiteCollectionEntity within the specified project
	 * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
	 * @return A list of all instances of TestSuiteCollectionEntity
	 * @throws ResourceException if KS could not read or access any of the instances
	 * 
	 * @since 1.0.10
	 * 
	 */
	List<TestSuiteCollectionEntity> getAllTestSuiteCollectionsInProject(ProjectEntity project) throws ResourceException;
	
    /**
     * Returns an instance of TestSuiteCollectionEntity of the given <code>project</code> by the given <code>testCaseId</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param testSuiteCollectionId id of TestSuiteCollectionEntity
     * @return an instance of TestSuiteCollectionEntity
     * @throws ResourceException if KS could not read or access test suite.
     * 
     * @since 1.0.10
     */
	TestSuiteCollectionEntity getTestSuiteCollection(ProjectEntity project, String testSuiteCollectionId) throws ResourceException;
	
	/**
	 * Allows you to add an existing Test Suite into a Test Suite Collection
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param testSuiteCollectionId id of TestSuiteCollectionEntity
	 * @param testSuiteId id of the TestSuiteEntity
	 * @return An instance of TestSuiteCollectionEntit which has the specified Test Suite
	 * @throws ResourceException if KS cannot read or access either the test suite collection or the test suite
	 */
	TestSuiteCollectionEntity addTestSuiteToTestSuiteCollection(ProjectEntity project, String testSuiteCollectionId, String testSuiteId)
	throws ResourceException;	
}
