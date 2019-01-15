package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.ReportEntity;
import com.katalon.platform.api.report.TestSuiteRecord;
import com.katalon.platform.api.service.ProjectManager;

/**
 * ReportController is a unique KS Controller to help KS plugins can manipulate {@link ReportEntity} in the file
 * system.
 * 
 * @see #getTestSuiteRecord(ProjectEntity, ReportEntity)
 * @see #updateIntegration(ProjectEntity, ReportEntity, Integration)
 * @see #getReport(ProjectEntity, String)
 * 
 * @since 1.0.4
 */
public interface ReportController extends Controller {

    /**
     * Returns an instance of ReportEntity of the given <code>project</code> by the give <code>reportId</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param reportId id of ReportEntity
     * @return an instance of ReportEntity
     * @throws ResourceException thrown if KS could not access information of the ReportEntity in file system
     * @since 1.0.4
     */
    ReportEntity getReport(ProjectEntity project, String reportId) throws ResourceException;

    /**
     * Updates the give <code>report</code> of the given <code>project</code> with the new <code>integration</code> into
     * KS file system.
     * 
     * @param project the working project. The current working project can retrieve by
     * {@link ProjectManager#getCurrentProject()}
     * @param report the report needs to be updated
     * @param integration the new integration description
     * @return the updated {@link ReportEntity} contains the given <code>integration</code> after saving.
     * @throws ResourceException thrown if KS could not update the report.
     * 
     * @since 1.0.4
     */
    ReportEntity updateIntegration(ProjectEntity project, ReportEntity report, Integration integration)
            throws ResourceException;

    /**
     * Returns an instance of TestSuiteRecord that contains all executed logs of a execute test suite.
     * 
     * @param project the working project. The current working project can retrieve by
     * {@link ProjectManager#getCurrentProject()}
     * @param report the report entity of TestSuiteRecord
     * @return an instance of TestSuiteRecord
     * @throws ResourceException thrown if KS could not read logs the report.
     */
    TestSuiteRecord getTestSuiteRecord(ProjectEntity project, ReportEntity report) throws ResourceException;
}
