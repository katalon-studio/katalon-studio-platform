package com.katalon.platform.internal;


import com.katalon.platform.api.model.Entity;
import com.katalon.platform.api.service.ProjectManager;

public class ProjectManagerImpl implements ProjectManager {
	private Entity currentProject;
	@Override
	public Entity getCurrentProject() {
		return currentProject;
	}
	
	public void setCurrentProject(Entity project){
		currentProject = project;
	}

}
