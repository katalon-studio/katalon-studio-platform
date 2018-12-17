package com.katalon.platform.api.extension.execution.impl;

import com.katalon.platform.api.extension.execution.ExecutionEvent;
import com.katalon.platform.api.extension.execution.TestSuiteCollectionExecutionContext;

public class TestSuiteCollectionExecutionEvent implements ExecutionEvent {
    private final String eventType;

    private final TestSuiteCollectionExecutionContext context;

    public TestSuiteCollectionExecutionEvent(String eventType, TestSuiteCollectionExecutionContext context) {
        this.eventType = eventType;
        this.context = context;
    }

    @Override
    public String getEventName() {
        return eventType;
    }

    @Override
    public TestSuiteCollectionExecutionContext getExecutionContext() {
        return context;
    }
}
