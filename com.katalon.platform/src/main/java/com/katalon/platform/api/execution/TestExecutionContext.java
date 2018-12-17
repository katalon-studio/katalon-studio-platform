package com.katalon.platform.api.execution;

public interface TestExecutionContext {
    String getId();
    
    String getSourceId();

    long getStartTime();

    long getEndTime();
}
