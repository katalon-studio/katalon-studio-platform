package com.katalon.platform.api.execution;

public interface TestCaseExecutionContext extends TestExecutionContext {

    String getTestCaseStatus();

    String getMessage();
}
