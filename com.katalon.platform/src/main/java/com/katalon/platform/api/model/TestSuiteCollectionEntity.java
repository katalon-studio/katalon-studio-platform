package com.katalon.platform.api.model;

import java.util.List;

public interface TestSuiteCollectionEntity extends Entity {
	
    String getParentFolderId();

    String getDescription();

    String getComment();

    String getTags();
	
	List<TestSuiteEntity> getTestSuites();
	
}
