package com.katalon.platform.api.extension.execution;

public interface TestCaseExecutionContext extends TestExecutionContext {

    String getTestCaseStatus();

    String getMessage();
}
