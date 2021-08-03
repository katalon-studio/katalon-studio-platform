package com.katalon.platform.api.execution;

import java.util.List;

public interface TestSuiteCollectionExecutionContext extends TestExecutionContext {

    String getReportId();

    String getReportLocation();

    String getReportFolderLocation();

    List<TestSuiteExecutionContext> getTestSuiteResults();
}
