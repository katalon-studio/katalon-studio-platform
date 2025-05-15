package com.katalon.platform.api.execution;

import java.util.List;

public interface TestSuiteExecutionContext extends TestExecutionContext {

    String getReportId();

    String getReportLocation();
    
    String getHostName();

    String getOs();

    String getBrowser();

    String getDeviceId();

    String getDeviceName();

    String getSuiteName();

    String getExecutionProfile();
    
    List<TestCaseExecutionContext> getTestCaseContexts();
}
