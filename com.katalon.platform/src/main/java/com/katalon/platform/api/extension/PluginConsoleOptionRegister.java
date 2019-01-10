package com.katalon.platform.api.extension;

import java.util.List;

import com.katalon.platform.api.model.PluginConsoleOption;
import com.katalon.platform.api.model.TestCaseEntity;

public interface PluginConsoleOptionRegister {

	/**
	 * This method is intended to be implemented by clients to decide additional 
	 * console options that will be recognized during console pre-execution
	 * 
	 * @return A list of console options
	 */
	List<PluginConsoleOption<?>> getPluginConsoleOptionList();
	
	/**
	 * This method is intended to be implemented by clients to select test cases they want to be executed
	 * in a test suite
	 * 
	 * @param pluginConsoleOption A console option recognized during console pre-execution phase
	 * @param testCases The list of test cases of the current to-be-launched test suites
	 * @return A list of test cases that will be executed instead
	 */
	List<TestCaseEntity> filterTestCasesOnPluginConsoleOptionRecognized(PluginConsoleOption<?> pluginConsoleOption, List<TestCaseEntity> testCases);
		
}
