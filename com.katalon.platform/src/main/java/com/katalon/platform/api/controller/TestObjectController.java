package com.katalon.platform.api.controller;

import java.util.List;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestObjectEntity;

public interface TestObjectController extends Controller {
	
	List<TestObjectEntity> getTestObjects(ProjectEntity projectEntity) throws ResourceException;
	
	TestObjectEntity getTestObject(ProjectEntity projectEntity, String testObjectId) throws ResourceException;

}
