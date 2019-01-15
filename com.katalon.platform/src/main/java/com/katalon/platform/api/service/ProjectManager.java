package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.model.ProjectEntity;

/**
 * ProjectManager is an interface that maintains list of <i>ProjectEntity</i>.
 * <p>
 * The unique instance of ProjectManager can access by using {@link Application#getProjectManager()}
 * 
 * @since 1.0.4
 */
public interface ProjectManager {
    /**
     * @return the current working project. Can be null if there is no opened project.
     * 
     * @since 1.0.4
     */
    ProjectEntity getCurrentProject();
}
