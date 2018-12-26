package com.katalon.platform.internal;


import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.service.ProjectManager;

public class ProjectManagerImpl implements ProjectManager {
	private ProjectEntity currentProject;
	@Override
	public ProjectEntity getCurrentProject() {
		return currentProject;
	}
	
	public void setCurrentProject(ProjectEntity project){
		currentProject = project;
	}

}
