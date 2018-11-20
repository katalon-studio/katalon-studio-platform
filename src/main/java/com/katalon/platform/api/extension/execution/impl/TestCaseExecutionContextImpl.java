package com.katalon.platform.api.extension.execution.impl;

import com.katalon.platform.api.extension.execution.TestCaseExecutionContext;

public class TestCaseExecutionContextImpl implements TestCaseExecutionContext {
    
    private final Builder builder;
    
    private TestCaseExecutionContextImpl(Builder builder) {
        this.builder = builder;
    }

    @Override
    public String getId() {
        return builder.id;
    }

    @Override
    public String getSourceId() {
        return builder.sourceId;
    }

    @Override
    public long getStartTime() {
        return builder.startTime;
    }

    @Override
    public long getEndTime() {
        return builder.endTime;
    }

    @Override
    public String getTestCaseStatus() {
        return builder.testCaseStatus;
    }

    @Override
    public String getMessage() {
        return builder.message;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public static class Builder {
        private String id;
        
        private String sourceId;
        
        private long startTime;
        
        private long endTime;
        
        private String testCaseStatus;
        
        private String message;
        
        private Builder(String id, String sourceId) {
            this.id = id;
            this.sourceId = sourceId;
        }
        
        public static Builder create(String id, String sourceId) {
            return new Builder(id, sourceId);
        }
        
        public Builder withStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }
        
        public Builder withEndTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder withTestCaseStatus(String testCaseStatus) {
            this.testCaseStatus = testCaseStatus;
            return this;
        }
        
        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }
     
        public TestCaseExecutionContextImpl build() {
            return new TestCaseExecutionContextImpl(this);
        }
    }
}
