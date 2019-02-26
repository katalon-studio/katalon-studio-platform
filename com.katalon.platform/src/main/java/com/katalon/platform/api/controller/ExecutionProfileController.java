package com.katalon.platform.api.controller;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.ExecutionProfileEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * ExecutionProfileController is a unique KS Controller to help KS plugins can manipulate Execution Profiles (Global
 * Variable) in the file system.
 * 
 * @see #getAllProfiles(ProjectEntity)
 * @see #getProfile(ProjectEntity, String)
 * @see #newProfile(ProjectEntity, String)
 * 
 * @since 1.0.7
 */
public interface ExecutionProfileController {

    /**
     * Returns all execution profiles of the given <code>project</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @return list of ExecutionProfileEntity. The list can be empty but not null.
     * 
     * @throws ResourceException if KS could not read or access profiles.
     * 
     * @since 1.0.7
     */
    List<ExecutionProfileEntity> getAllProfiles(ProjectEntity project) throws ResourceException;;

    /**
     * Returns an instance of ExecutionProfileEntity of the given <code>project</code> by the given
     * <code>profileName</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param profileName name of the ExecutionProfileEntity
     * @return an instance of ExecutionProfileEntity
     * @throws ResourceException if KS could not read or access the profile.
     * 
     * @since 1.0.7
     */
    ExecutionProfileEntity getProfile(ProjectEntity project, String profileName) throws ResourceException;;

    /**
     * Create a new ExecutionProfileEntity under <code>Profiles</code> folder with the given <code>profileName</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param profileName name of the ExecutionProfileEntity
     * @return new created ExecutionProfileEntity
     * @throws ResourceException if KS could not create profile.
     * 
     * @since 1.0.7
     */
    ExecutionProfileEntity newProfile(ProjectEntity project, String profileName) throws ResourceException;
}
