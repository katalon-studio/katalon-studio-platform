package com.katalon.platform.api.extension.execution;

import java.util.List;

public interface TestSuiteCollectionExecutionContext extends TestExecutionContext {
    List<TestSuiteCollectionExecutionContext> getTestSuiteResults();
}
