package com.katalon.platform.api.controller;

import java.util.Collections;
import java.util.Map;

import com.katalon.platform.api.exception.PlatformException;

/**
 * TestExecutionController is a unique KS Controller to help KS plugins can create a test execution (test suite or test
 * suite collection) via KS commandline arguments system.
 *
 * @since 1.0.10
 */
public interface TestExecutionController extends Controller {

    /**
     * Creates a UI test execution by the given <code>args</code>.
     * <br>
     * Supports almost commands that are listed in
     * <a href="https://docs.katalon.com/katalon-studio/docs/console-mode-execution.html#katalon-command-line-options">
     * Katalon Command Line options</a>
     * <p>
     * Supported arguments:
     * <ul>
     * <li>testSuitePath</li>
     * <li>testSuiteCollectionPath</li>
     * <li>browserType</li>
     * <li>retry</li>
     * <li>retryFailedTestCases</li>
     * <li>remoteWebDriverUrl</li>
     * <li>remoteWebDriverType</li>
     * <li>deviceId</li>
     * <li>config</li>
     * <li>executionProfile</li>
     * <li>g_XXX</li>
     * </ul>
     * @param args Array of arguments
     * <p>
     * Examples of using:
     * <br>
     * 1: Execute test suite A with <b>Chrome</b> browser:
     * <br>
     * <code>args = ["-testSuitePath=path_to_A", "-browserType=Chrome"]</code>
     * <br>
     * <br>
     * 2: Execute test suite A with <b>Android</b> device <b>emulator-5554</b>:
     * <br>
     * <code>args = ["-testSuitePath=path_to_A", "-browserType=Android", "deviceId=emulator-5554"]</code>
     * <br>
     * <br>
     * 3: Execute test suite collection B:
     * <br>
     * <code>args = ["-testSuiteCollectionPath=path_to_B"]</code>
     * @throws PlatformException if the args are invalid.
     * @since 1.0.10
     */
    public void run(String[] args) throws PlatformException;

    /**
     * Creates a UI test execution by the given <code>args</code>.
     * <br>
     * Supports almost commands that are listed in
     * <a href="https://docs.katalon.com/katalon-studio/docs/console-mode-execution.html#katalon-command-line-options">
     * Katalon Command Line options</a>.
     * 
     * <p>
     * Supported arguments:
     * <ul>
     * <li>testSuitePath</li>
     * <li>testSuiteCollectionPath</li>
     * <li>browserType</li>
     * <li>retry</li>
     * <li>retryFailedTestCases</li>
     * <li>remoteWebDriverUrl</li>
     * <li>remoteWebDriverType</li>
     * <li>deviceId</li>
     * <li>config</li>
     * <li>executionProfile</li>
     * <li>g_XXX</li>
     * </ul>
     * 
     * @param args Array of arguments
     * <p>
     * Examples of using:
     * <br>
     * 1: Execute test suite A with <b>Chrome</b> browser:
     * <br>
     * <code>args = ["-testSuitePath=path_to_A", "-browserType=Chrome"]</code>
     * <br>
     * <br>
     * 2: Execute test suite A with <b>Android</b> device <b>emulator-5554</b>:
     * <br>
     * <code>args = ["-testSuitePath=path_to_A", "-browserType=Android", "deviceId=emulator-5554"]</code>
     * <br>
     * <br>
     * 3: Execute test suite collection B:
     * <br>
     * <code>args = ["-testSuiteCollectionPath=path_to_B"]</code>
     * 
     * @param testSuiteInstanceConfiguration contains some configurations for a test suite execution instance.
     * @throws PlatformException if the args are invalid.
     * @since 1.0.10
     */
    public void run(String[] args, TestSuiteInstanceConfiguration testSuiteInstanceConfiguration)
            throws PlatformException;

    /**
     * Describes some configurations of a test execution instance (test suite) such as: VM arguments, environment
     * variables,...
     * 
     * @since 1.0.10
     */
    public interface TestSuiteInstanceConfiguration {

        /**
         * @return an array of VM arguments for a test execution instance.
         * @since 1.0.10
         */
        default String[] getVmArgs() {
            return new String[0];
        }

        /**
         * @return a map of environment variables of the runtime test execution.
         * @since 1.0.10
         */
        default Map<String, String> getAdditionEnvironmentVariables() {
            return Collections.emptyMap();
        }

        /**
         * @return a map of additional data that will use in TestSuiteExecutor.
         * @since 1.0.10
         */
        default Map<String, String> getAdditionalData() {
            return Collections.emptyMap();
        }
    }
}
