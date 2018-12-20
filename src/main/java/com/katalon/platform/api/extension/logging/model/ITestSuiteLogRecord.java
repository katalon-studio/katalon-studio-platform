package com.katalon.platform.api.extension.logging.model;

import java.util.List;
import java.util.Map;

public interface ITestSuiteLogRecord extends ILogRecord {
    
    String getBrowser();

    String getLogFolder();

    int getTotalTestCases();

    int getTotalPassedTestCases();

    int getTotalFailedTestCases();

    int getTotalErrorTestCases();

    int getTotalIncompleteTestCases();

    int getTotalTestCasesWithTestStatusValue();

    String getDeviceName();

    String getDeviceId();

    String getDevicePlatform();

    String getOs();

    String getHostName();

    String getAppVersion();

    Map<String, String> getRunData();

    <T extends ILogRecord> int getChildIndex(T child);

    List<String> getLogFiles();
}
