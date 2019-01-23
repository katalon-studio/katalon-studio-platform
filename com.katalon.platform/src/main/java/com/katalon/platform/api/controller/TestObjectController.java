package com.katalon.platform.api.controller;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestObjectEntity;
import com.katalon.platform.api.model.TestObjectEntity.TestObjectProperty;
import com.katalon.platform.api.model.TestObjectEntity.TestObjectXPath;

public interface TestObjectController extends Controller {
	
	TestObjectEntity getTestObject(ProjectEntity projectEntity, String testObjectId) throws ResourceException;
	
	TestObjectEntity newTestObject(ProjectEntity projectEntity, FolderEntity parentFolder, String newTestObjectName) throws ResourceException;
	
	TestObjectEntity updateTestObject(ProjectEntity projectEntity, FolderEntity parentFolder, TestObjectDescription newTestObject) throws ResourceException;
	
	
	public interface TestObjectDescription {
		List<TestObjectProperty> getProperties();
		
		List<TestObjectXPath> getXPaths();
	}
	
}
