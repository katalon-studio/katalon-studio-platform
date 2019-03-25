package com.katalon.platform.api.controller;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * FolderController is a unique KS Controller to help KS plugins can manipulate {@link FolderEntity} in the file
 * system.
 * 
 * @see #getFolder(ProjectEntity, String)
 * @see #newFolder(ProjectEntity, FolderEntity, String)
 * @see #getAvailableFolderName(ProjectEntity, FolderEntity, String)
 * @see #getChildTestCases(ProjectEntity, FolderEntity)
 * 
 * @since 1.0.4
 */
public interface FolderController extends Controller {
    /**
     * Returns an instance of FolderEntity of the given <code>project</code> by the give <code>folderId</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param folderId id of FolderEntity
     * @return an instance of FolderEntity
     * @throws ResourceException thrown if KS could not access information of the FolderEntity in the file system
     * @since 1.0.4
     */
    FolderEntity getFolder(ProjectEntity project, String folderId) throws ResourceException;

    /**
     * Creates new folder under the given <code>parentFolder</code> with a specified <code>name</code>
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder of new folder
     * @param name name of new folder. Name should not be blank or content non-English characters. Please use
     * {@link #getAvailableFolderName(ProjectEntity, FolderEntity, String)} before using.
     * @return new created FolderEntity
     * @throws ResourceException thrown if name is duplicated or invalid format, or KS could not create new folder in
     * the file system.
     * 
     * @since 1.0.4
     */
    FolderEntity newFolder(ProjectEntity project, FolderEntity parentFolder, String name) throws ResourceException;

    /**
     * Returns an available name for the given <i>name</i>
     * <p>
     * Sample of using: We want to create a folder with name <b>New Folder</b> under root folder <i>Test Cases</i>
     * <br>
     * Case 1: There is no <b>New Folder</b> folder under <code>Test Cases</code> then the result is <b>New
     * Folder</b>
     * <br>
     * Case 2: <b>New Folder</b> folder exists, but <b>New Folder (1)</b> doesn't then the result is <b>New Folder
     * (1)</b>
     * <br>
     * Case 3: <b>New Folder</b> and <b>New Folder (1)</b> folder exist, but <b>New Folder (2)</b> doesn't then the
     * result is <b>New Folder (2)</b>
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder that needs to check name
     * @param name name of new folder. Name should not be blank or content non-English characters.
     * @return a new available name
     * @throws ResourceException thrown if name is invalid format, or KS could not read or access parent folder.
     * 
     * @since 1.0.4
     */
    String getAvailableFolderName(ProjectEntity project, FolderEntity parentFolder, String name)
            throws ResourceException;

    /**
     * List the children test cases under a folder.
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder that needs to check.
     * @return list of TestCaseEntity. The list can be empty but not null.
     * @throws ResourceException thrown if KS could not read or access test cases under parent folder.
     * 
     * @since 1.0.4
     */
    List<TestCaseEntity> getChildTestCases(ProjectEntity project, FolderEntity parentFolder) throws ResourceException;

    /**
     * List the children folders under a folder.
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder that needs to check.
     * @return list of FolderEntity. The list can be empty but not null.
     * @throws ResourceException thrown if KS could not read or access test cases under parent folder.
     * 
     * @since 1.0.7
     */
    List<FolderEntity> getChildFolders(ProjectEntity project, FolderEntity parentFolder) throws ResourceException;
}
