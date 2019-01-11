package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;

public interface ProjectController extends Controller {
    ProjectEntity updateIntegration(ProjectEntity project, Integration integration) throws ResourceException;
}