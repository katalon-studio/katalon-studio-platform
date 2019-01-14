package com.katalon.platform.api.extension;

import java.util.List;

import com.katalon.platform.api.console.PluginConsoleOption;
import com.katalon.platform.api.model.TestCaseEntity;

public interface LauncherOptionParserDescription {
	 String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.launcherOptionParserDescription";
	 
	 List<PluginConsoleOption<?>> getConsoleOptionList();
	 
	 void onConsoleOptionDetected(PluginConsoleOption<?> detectedConsoleOption);
	 
	 default List<TestCaseEntity> onPreExecution(List<TestCaseEntity> testCases){
		 return testCases;
	 }
	 
}
