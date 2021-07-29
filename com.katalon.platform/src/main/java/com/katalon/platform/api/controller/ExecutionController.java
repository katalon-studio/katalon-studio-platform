package com.katalon.platform.api.controller;

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

}
