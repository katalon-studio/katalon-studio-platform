package com.katalon.platform.api.event;

import com.katalon.platform.api.execution.TestExecutionContext;

public interface ExecutionEvent {

    String KATALON_EXECUTION_EVENT_ALL_TOPIC = "KATALON_EXECUTION/*";

    String TEST_SUITE_STARTED_EVENT = "KATALON_EXECUTION/TEST_SUITE_STARTED";

    String TEST_SUITE_FINISHED_EVENT = "KATALON_EXECUTION/TEST_SUITE_FINISHED";

    String TEST_SUITE_COLLECTION_STARTED_EVENT = "KATALON_EXECUTION/TEST_SUITE_COLLECTION_STARTED";

    String TEST_SUITE_COLLECTION_FINISHED_EVENT = "KATALON_EXECUTION/TEST_SUITE_COLLECTION_FINISHED";

    String getEventName();

    TestExecutionContext getExecutionContext();
}
