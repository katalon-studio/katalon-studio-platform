package com.katalon.platform.api.report;

public interface LogRecord {
    String getName();

    String getDescription();

    String getMessage();

    long getStartTime();

    long getEndTime();
}