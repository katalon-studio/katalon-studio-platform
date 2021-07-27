package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;

public interface ExecutionController extends Controller {
    /**
     * @return the running mode: IDE or KRE
     * 
     * @since 1.0.18
     */
    public String getRunningMode();

    /**
     * @return JRE location
     * 
     * @since 1.0.18
     */
    public String getJreLocation();

    /**
     * @return JUnit report folder location
     * @throws ResourceException if KS could not access information of the ReportEntity in file system
     * 
     * @since 1.0.18
     */
    public String getJunitReportLocation() throws ResourceException;
}
