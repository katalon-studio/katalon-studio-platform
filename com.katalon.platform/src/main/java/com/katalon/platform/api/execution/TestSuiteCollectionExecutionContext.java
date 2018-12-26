package com.katalon.platform.api.execution;

import java.util.List;

public interface TestSuiteCollectionExecutionContext extends TestExecutionContext {
    String getReportLocation();

    List<TestSuiteExecutionContext> getTestSuiteResults();
}
