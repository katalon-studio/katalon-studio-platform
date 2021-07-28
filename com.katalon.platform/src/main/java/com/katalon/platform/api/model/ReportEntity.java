package com.katalon.platform.api.model;

public interface ReportEntity extends Entity, HasIntegration {

    /**
     * @return JUnit report file location
     * 
     * @since 1.0.18
     */
    public String getJunitReportLocation();

}
