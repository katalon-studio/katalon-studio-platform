package com.katalon.platform.internal.execution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.katalon.platform.api.execution.TestCaseExecutionContext;
import com.katalon.platform.api.execution.TestSuiteExecutionContext;

public class TestSuiteExecutionContextImpl implements TestSuiteExecutionContext {
    private final Builder builder;

    private TestSuiteExecutionContextImpl(Builder builder) {
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
    public String getReportLocation() {
        return builder.reportLocation;
    }

    @Override
    public List<TestCaseExecutionContext> getTestCaseContexts() {
        return Collections.unmodifiableList(builder.testCaseContexts);
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

        private String reportLocation;

        private List<TestCaseExecutionContext> testCaseContexts = new ArrayList<>();

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
        
        public Builder withReportLocation(String reportLocation) {
            this.reportLocation = reportLocation;
            return this;
        }
        
        public Builder withTestCaseContext(List<TestCaseExecutionContext> testCaseContexts) {
            this.testCaseContexts = testCaseContexts;
            return this;
        }

        public TestSuiteExecutionContextImpl build() {
            return new TestSuiteExecutionContextImpl(this);
        }
    }
}
