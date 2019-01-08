package com.katalon.platform.api.report;

import java.util.List;
import java.util.Map;

public interface TestSuiteRecord extends LogRecord {
    
    String getReportId();

    String getTestSuiteId();

    List<TestCaseRecord> getTestCaseRecords();

    Map<String, String> getRunData();

    List<String> getLogFiles();

    List<String> getAttachments();
}
