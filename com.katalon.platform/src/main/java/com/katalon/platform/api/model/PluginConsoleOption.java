package com.katalon.platform.api.model;

public interface PluginConsoleOption<T> {
    /**
     * Get the type class of the console option argument for type check
     * @return the type class of the console option argument
     */
    Class<T> getArgumentType();
    
    /**
     * Return true if the console option has argument, false if not
     * @return true if the console option has argument, false if not
     */
    boolean hasArgument();
    
    /**
     * Get the name represent the console option
     * @return the name represent the console option
     */
    String getOption();
    
    /**
     * Get the default value for the argument, if null then the argument has no default value
     * @return the default value for the argument, if null then the argument has no default value
     */
    public String getDefaultArgumentValue();
    
    /**
     * Get the require flag for this console option
     * @return true if the option is required; otherwise false
     */
    public boolean isRequired();

    T getValue();
    
    void setValue(String rawValue);
}
