package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.ReportCollectionEntity;

/**
 * ReportCollectionController is a unique KS Controller for KS plugins to manipulate {@link ReportCollectionEntity} in
 * the file system.
 * 
 * @see #getReport(String)
 * 
 * @since 1.0.18
 */
public interface ReportCollectionController extends Controller {

    /**
     * Returns an instance of ReportCollectionEntity of the given <code>reportFolderPath</code>
     * 
     * @param reportFolderPath of ReportCollectionEntity
     * @return an instance of ReportCollectionEntity
     * @throws ResourceException thrown if KS could not access information of the ReportCollectionEntity in file system
     * @since 1.0.18
     */
    ReportCollectionEntity getReport(String reportFolderPath) throws ResourceException;

}
