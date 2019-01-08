package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.ReportEntity;
import com.katalon.platform.api.report.TestSuiteRecord;

public interface ReportController extends Controller {
    
    ReportEntity getReport(ProjectEntity project, String reportId)
            throws ResourceException;

    ReportEntity updateIntegration(ProjectEntity project, ReportEntity report, Integration integration)
            throws ResourceException;

    TestSuiteRecord getTestSuiteRecord(ProjectEntity project, ReportEntity report) throws ResourceException;
}