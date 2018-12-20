package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.preference.PluginPreference;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.api.service.PreferenceManager;

public class PreferenceManagerImpl implements PreferenceManager {

    private Map<String, Map<String, PluginPreference>> lookup = new HashMap<>();

    @Override
    public PluginPreference getPluginPreference(String projectId, String pluginId) throws ResourceException {
        if (!projectId.equals(ApplicationManager.getInstance().getProjectManager().getCurrentProject().getId())) {
            throw new ResourceException(String.format("Project %s not found", projectId));
        }
        
        if (ApplicationManager.getInstance().getPluginManager().getPlugin(pluginId) == null) {
            throw new ResourceException(String.format("Plugin %s not found", pluginId));
        }

        if (lookup.containsKey(projectId)) {
            Map<String, PluginPreference> preferenceLookup = lookup.get(projectId);
            if (preferenceLookup.containsKey(pluginId)) {
                return preferenceLookup.get(pluginId);
            }
            PluginPreference pluginPreference = new PluginPreferenceImpl(projectId, pluginId);
            preferenceLookup.put(pluginId, pluginPreference);
            return pluginPreference;
        }
        PluginPreference pluginPreference = new PluginPreferenceImpl(projectId, pluginId);
        Map<String, PluginPreference> preferenceLookup = new HashMap<>();
        preferenceLookup.put(pluginId, pluginPreference);
        lookup.put(projectId, preferenceLookup);
        return pluginPreference;
    }

}
