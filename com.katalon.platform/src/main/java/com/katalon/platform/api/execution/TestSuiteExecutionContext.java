package com.katalon.platform.api.execution;

import java.util.List;

public interface TestSuiteExecutionContext extends TestExecutionContext {

    String getReportId();

    String getReportLocation();
    
    List<TestCaseExecutionContext> getTestCaseContexts();
}
