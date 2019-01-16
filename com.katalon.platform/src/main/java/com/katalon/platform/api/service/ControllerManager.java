package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.controller.Controller;
import com.katalon.platform.api.controller.FolderController;
import com.katalon.platform.api.controller.ProjectController;
import com.katalon.platform.api.controller.ReportController;
import com.katalon.platform.api.controller.TestCaseController;

/**
 * ControllerManager is a unique instance interface that maintains list of registered <i>Controller</i>.
 * <p>
 * Each Controller is a KS platform service to help plugins interact, manipulate KS test artifacts, such as:
 * TestCaseEntity, TestSuiteEntity, ProjectEnity, ReportEntity...
 * <p>
 * The unique instance of ControllerManager can access by using {@link Application#getControllerManager()}
 */
public interface ControllerManager {
    /**
     * Returns the Controller associated with the given class.
     * 
     * @param <T> the type of Controller
     * @param clazz a interface Controller class that needs to be found in the ControllerManager
     * @see TestCaseController
     * @see FolderController
     * @see ReportController
     * @see ProjectController
     * 
     * @return an implementation instance of Controller corresponding to the given <code>class</code>, or
     * <code>null</code> if not found.
     * @since 1.0.4
     */
    <T extends Controller> T getController(Class<T> clazz);
}
