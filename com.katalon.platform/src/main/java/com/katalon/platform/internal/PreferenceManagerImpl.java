package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.InstanceScope;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.Entity;
import com.katalon.platform.api.preference.ApplicationPreference;
import com.katalon.platform.api.preference.PluginPreference;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.api.service.PreferenceManager;
import com.katalon.platform.internal.preference.ApplicationPreferenceImp;
import com.katalon.platform.internal.preference.InternalPluginPreferenceImpl;
import com.katalon.platform.internal.preference.PluginPreferenceImpl;
import com.katalon.platform.internal.preference.ScopedPreferenceStore;

public class PreferenceManagerImpl implements PreferenceManager {

    private Map<String, Map<String, PluginPreference>> pluginPreferences = new HashMap<>();
    
    private Map<String, ApplicationPreference> applicationPreferences = new HashMap<>();

    @Override
    public PluginPreference getPluginPreference(String projectId, String pluginId) throws ResourceException {
        Entity projectEntity = ApplicationManager.getInstance().getProjectManager().getCurrentProject();
        if (!projectId.equals(ApplicationManager.getInstance().getProjectManager().getCurrentProject().getId())) {
            throw new ResourceException(String.format("Project %s not found", projectId));
        }

        if (ApplicationManager.getInstance().getPluginManager().getPlugin(pluginId) == null) {
            throw new ResourceException(String.format("Plugin %s not found", pluginId));
        }

        if (pluginPreferences.containsKey(projectId)) {
            Map<String, PluginPreference> preferenceLookup = pluginPreferences.get(projectId);
            if (preferenceLookup.containsKey(pluginId)) {
                return preferenceLookup.get(pluginId);
            }
            PluginPreference pluginPreference = new PluginPreferenceImpl(projectEntity, pluginId);
            preferenceLookup.put(pluginId, pluginPreference);
            return pluginPreference;
        }
        PluginPreference pluginPreference = new PluginPreferenceImpl(projectEntity, pluginId);
        Map<String, PluginPreference> preferenceLookup = new HashMap<>();
        preferenceLookup.put(pluginId, pluginPreference);
        pluginPreferences.put(projectId, preferenceLookup);
        return pluginPreference;
    }

    @Override
    public ApplicationPreference getApplicationPreference(String pluginId) {
        if (applicationPreferences.containsKey(pluginId)) {
            return applicationPreferences.get(pluginId);
        }
        ScopedPreferenceStore scopedPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, pluginId);
        ApplicationPreference preference = new ApplicationPreferenceImp(pluginId, scopedPreferenceStore);
        applicationPreferences.put(pluginId, preference);
        return preference;
    }

    @Override
    public PluginPreference getInternalPluginPreference(String projectId, String internalPluginId)
            throws ResourceException {
        Entity projectEntity = ApplicationManager.getInstance().getProjectManager().getCurrentProject();
        if (!projectId.equals(ApplicationManager.getInstance().getProjectManager().getCurrentProject().getId())) {
            throw new ResourceException(String.format("Project %s not found", projectId));
        }

        if (ApplicationManager.getInstance().getPluginManager().getPlugin(internalPluginId) == null) {
            throw new ResourceException(String.format("Plugin %s not found", internalPluginId));
        }

        if (pluginPreferences.containsKey(projectId)) {
            Map<String, PluginPreference> preferenceLookup = pluginPreferences.get(projectId);
            if (preferenceLookup.containsKey(internalPluginId)) {
                return preferenceLookup.get(internalPluginId);
            }
            PluginPreference pluginPreference = new PluginPreferenceImpl(projectEntity, internalPluginId);
            preferenceLookup.put(internalPluginId, pluginPreference);
            return pluginPreference;
        }
        PluginPreference pluginPreference = new InternalPluginPreferenceImpl(projectEntity, internalPluginId);
        Map<String, PluginPreference> preferenceLookup = new HashMap<>();
        preferenceLookup.put(internalPluginId, pluginPreference);
        pluginPreferences.put(projectId, preferenceLookup);
        return pluginPreference;
    }

}
