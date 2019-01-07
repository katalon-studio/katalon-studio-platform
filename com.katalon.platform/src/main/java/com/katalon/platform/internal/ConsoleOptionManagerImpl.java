package com.katalon.platform.internal;

import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.PluginConsoleOptionContributor;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.api.service.ConsoleOptionManager;

public class ConsoleOptionManagerImpl implements ConsoleOptionManager {
	private Map<String, Map<String, PluginConsoleOptionContributor>> lookup = new HashMap<>();

    @Override
    public PluginConsoleOptionContributor getConsoleOption(String projectId, String pluginId) throws ResourceException {
        if (!projectId.equals(ApplicationManager.getInstance().getProjectManager().getCurrentProject().getId())) {
            throw new ResourceException(String.format("Project %s not found", projectId));
        }

        if (ApplicationManager.getInstance().getPluginManager().getPlugin(pluginId) == null) {
            throw new ResourceException(String.format("Plugin %s not found", pluginId));
        }

        if (lookup.containsKey(projectId)) {
            Map<String, PluginConsoleOptionContributor> consoleOptionContributorLookup = lookup.get(projectId);
            if (consoleOptionContributorLookup.containsKey(pluginId)) {
                return consoleOptionContributorLookup.get(pluginId);
            }
            PluginConsoleOptionContributor consoleOptionContributor = new PluginConsoleOptionContributorImpl();
            consoleOptionContributorLookup.put(pluginId, consoleOptionContributor);
            return consoleOptionContributor;
        }
        PluginConsoleOptionContributor consoleOptionContributor = new PluginConsoleOptionContributorImpl();
        Map<String, PluginConsoleOptionContributor> consoleOptionContributorLookup = new HashMap<>();
        consoleOptionContributorLookup.put(pluginId, consoleOptionContributor);
        lookup.put(projectId, consoleOptionContributorLookup);
        return consoleOptionContributor;
    }
	
}
