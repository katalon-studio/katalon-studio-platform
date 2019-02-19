package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.SystemFileEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * FeatureFileController is a unique KS Controller to help KS plugins can manipulate Feature Files (.feature) under Include/features folder in the file
 * system.
 * 
 * @see #getFeatureFile(ProjectEntity, String)
 * @see #newFeatureFile(ProjectEntity, String, String)
 * @see #getAvailableFeatureFileName(ProjectEntity, String)
 * 
 * @since 1.0.6
 */
public interface FeatureFileController extends Controller {

    /**
     * Returns an instance of Feature File as an SystemFileEntity by the give <code>featureFileName</code>. 
     * The create Feature File is under the <b>Include/features</b> folder.
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param featureFileId id of feature file
     * @return an instance of SystemFileEntity
     * @throws ResourceException if KS could not read or access feature file.
     * 
     * @since 1.0.6
     */
    SystemFileEntity getFeatureFile(ProjectEntity project, String featureFileId) throws ResourceException;

    /**
     * Create a new feature file under <code>Include/features</code> with the given <code>name</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param name name of the created feature file
     * @param parentFolder parent folder the feature file
     * @return new created Entity
     * @throws ResourceException if KS could not create test case.
     * 
     * @since 1.0.6
     */
    SystemFileEntity newFeatureFile(ProjectEntity project, FolderEntity parentFolder, String name) throws ResourceException;

    /**
     * Returns an available feature file name for the given <code>name</code> under the given <code>folder</code>
     * <p>
     * Sample of using: We want to create a folder with name <b>New Feature File.feature</b>
     * <br>
     * Case 1: There is no <b>New Feature File.feature</b> feature file that's under <code>parentFolder</code> then the result is 
     * <b>New Feature File.feature</b>
     * <br>
     * Case 2: <b>New Feature File.feature</b> feature file exists, but <b>New Feature File 1.feature</b> doesn't then the result is <b>New Feature File 1.feature</b>
     * <br>
     * Case 3: <b>New Feature File.feature</b> and <b>New Feature File 1.feature</b> folder exist, but <b>New Feature File 2.feature</b> doesn't then
     * the result is <b>New Feature File 2.feature</b>
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param name name of new feature file. Name should not be blank or content non-English characters.
     * @param parentFolder parent folder that needs to check name 
     * @return a new available name
     * @throws ResourceException thrown if name is invalid format, or KS could not read or access parent folder.
     * 
     * @since 1.0.6
     */
    String getAvailableFeatureFileName(ProjectEntity project, FolderEntity parentFolder, String name) throws ResourceException;
}
