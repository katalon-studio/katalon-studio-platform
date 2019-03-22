package com.katalon.platform.api.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.katalon.platform.api.execution.EnvironmentVariable;

public interface TestExecutionController {

    public void run(String[] args);

    public void run(String[] args, TestSuiteInstanceConfiguration testSuiteInstanceConfiguration);

    public interface TestSuiteInstanceConfiguration {
        default String getVmArgs() {
            return "";
        }

        default List<EnvironmentVariable> getEnvironmentVariables() {
            return Collections.emptyList();
        }

        default Map<String, String> getAdditionalData() {
            return Collections.emptyMap();
        }
    }
}