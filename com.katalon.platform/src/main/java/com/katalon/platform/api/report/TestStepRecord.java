package com.katalon.platform.api.report;

import java.util.List;

public interface TestStepRecord extends LogRecord, HasStatus {

    List<LogRecord> getChildRecords();

    List<String> getAttachments();
}