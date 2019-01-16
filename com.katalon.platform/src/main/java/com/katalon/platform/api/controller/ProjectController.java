package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * ProjectController is a unique KS Controller to help KS plugins can manipulate {@link ProjectEntity} in the file
 * system.
 * 
 * @see #updateIntegration(ProjectEntity, Integration)
 * 
 * @since 1.0.4
 */
public interface ProjectController extends Controller {
    /**
     * Updates the give <code>project</code> with the new <code>integration</code> into KS file system.
     * 
     * @param project the working project. The current working project can retrieve by
     * {@link ProjectManager#getCurrentProject()}
     * @param integration the new integration description
     * @return the updated {@link ProjectEntity} contains the given <code>integration</code> after saving.
     * @throws ResourceException thrown if KS could not update the project.
     * 
     * @since 1.0.4
     */
    ProjectEntity updateIntegration(ProjectEntity project, Integration integration) throws ResourceException;
}
