package com.katalon.platform.api.extension.execution.impl;

import com.katalon.platform.api.extension.execution.ExecutionEvent;
import com.katalon.platform.api.extension.execution.TestSuiteExecutionContext;

public class TestSuiteExecutionEvent implements ExecutionEvent {
    
    private final String eventName;
    
    private final TestSuiteExecutionContext context;
    
    public TestSuiteExecutionEvent(String eventName, TestSuiteExecutionContext context) {
        this.eventName = eventName;
        this.context = context;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public TestSuiteExecutionContext getExecutionContext() {
        return context;
    }
    
}
