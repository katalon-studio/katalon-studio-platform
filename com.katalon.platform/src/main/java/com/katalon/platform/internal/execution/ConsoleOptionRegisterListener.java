package com.katalon.platform.internal.execution;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.extension.PluginConsoleOptionRegister;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.api.service.ApplicationManager;

public class ConsoleOptionRegisterListener implements ExtensionListener {
	
    @Override
    public void register(Extension extension) {
    	if(extension.getImplementationClass() instanceof PluginConsoleOptionRegister){
    		PluginConsoleOptionRegister pluginConsoleOptionRegister = (PluginConsoleOptionRegister) extension.getImplementationClass();
    		ApplicationManager.getInstance()
    			.getConsoleManager()
    			.registerConsoleOptionList(extension.getPluginId(), pluginConsoleOptionRegister.getPluginConsoleOptionList());
    	}
    }
    
    @Override
    public void deregister(Extension extension) {
    	
    }
}
