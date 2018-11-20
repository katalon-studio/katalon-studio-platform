package com.katalon.platform.api.extension.execution;

import java.util.List;

public interface TestSuiteExecutionContext extends TestExecutionContext {

    String reportLocation();

    List<TestCaseExecutionContext> getTestCaseContexts();
}
