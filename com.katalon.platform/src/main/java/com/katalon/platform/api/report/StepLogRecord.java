package com.katalon.platform.api.report;

import java.util.List;

public interface StepLogRecord extends LogRecord, HasStatus {

    List<LogRecord> getChildRecords();

    String getStepStatus();

    List<String> getAttachments();
}