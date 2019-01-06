package com.katalon.platform.internal;

import java.util.ArrayList;
import java.util.List;

import com.katalon.platform.api.model.PluginConsoleOption;
import com.katalon.platform.api.model.PluginConsoleOptionContributor;

public class PluginConsoleOptionContributorImpl implements PluginConsoleOptionContributor{
	
	private List<PluginConsoleOption<?>> consoleOptions;
	
	public PluginConsoleOptionContributorImpl(){
		consoleOptions = new ArrayList<>();
	}
	
	public void setConsoleOptionList(List<PluginConsoleOption<?>> consoleOptions){
		this.consoleOptions = consoleOptions;
	}
	
	@Override
	public List<PluginConsoleOption<?>> getConsoleOptionList() {
		return consoleOptions;
	}

	@Override
	public void setArgumentValue(PluginConsoleOption<?> consoleOption, String argumentValue) throws Exception {
		if(consoleOptions.contains(consoleOption)){
			consoleOption.setValue(argumentValue);
		}
	}

}
