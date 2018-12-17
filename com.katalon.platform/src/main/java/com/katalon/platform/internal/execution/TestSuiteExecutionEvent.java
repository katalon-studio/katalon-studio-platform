package com.katalon.platform.internal.execution;

import com.katalon.platform.api.event.ExecutionEvent;
import com.katalon.platform.api.execution.TestSuiteExecutionContext;

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
