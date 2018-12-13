package com.katalon.platform.api.extension.execution;

public interface TestExecutionContext {
    String getId();
    
    String getSourceId();

    long getStartTime();

    long getEndTime();
}
