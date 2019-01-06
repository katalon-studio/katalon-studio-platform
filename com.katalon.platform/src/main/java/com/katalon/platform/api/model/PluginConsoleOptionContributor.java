package com.katalon.platform.api.model;

import java.util.List;

public interface PluginConsoleOptionContributor {
    /**
     * Get the console option list for this contributor
     * 
     * @return the console options list for this contributor
     */
    public List<PluginConsoleOption<?>> getConsoleOptionList();
    
    /**
     * Set the console options for this contributor
     */
    public void setConsoleOptionList(List<PluginConsoleOption<?>> consoleOptions);

    /**
     * Set the argument value from user input into the console option
     * 
     * @param argumentValue
     * @throws Exception
     */
    void setArgumentValue(PluginConsoleOption<?> consoleOption, String argumentValue) throws Exception;
}
