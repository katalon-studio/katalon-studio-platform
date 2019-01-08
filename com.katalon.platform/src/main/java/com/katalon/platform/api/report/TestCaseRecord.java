package com.katalon.platform.api.report;

import java.util.List;

public interface TestCaseRecord extends LogRecord, HasStatus {

    String getTestCaseId();

    List<LogRecord> getChildRecords();

    List<String> getAttachments();
}