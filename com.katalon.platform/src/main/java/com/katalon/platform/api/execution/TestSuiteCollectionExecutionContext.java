package com.katalon.platform.api.execution;

import java.util.List;

public interface TestSuiteCollectionExecutionContext extends TestExecutionContext {
    List<TestSuiteCollectionExecutionContext> getTestSuiteResults();
}
