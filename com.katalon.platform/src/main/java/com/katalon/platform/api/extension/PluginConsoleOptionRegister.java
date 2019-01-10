package com.katalon.platform.api.extension;

import java.util.List;

import com.katalon.platform.api.model.PluginConsoleOption;
import com.katalon.platform.api.model.TestCaseEntity;

public interface PluginConsoleOptionRegister {

	List<PluginConsoleOption<?>> getPluginConsoleOptionList();
	
	void filterTestCasesOnPluginConsoleOptionRecognized(PluginConsoleOption<?> pluginConsoleOption, List<TestCaseEntity> testCases);
		
}
