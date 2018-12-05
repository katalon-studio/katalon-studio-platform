package com.katalon.platform.api.extension.filter.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.filter.EntityFilterAdapter;
import com.katalon.platform.api.model.Entity;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.InternalActionManagerImpl;

public class EntityFilterAdapterListener implements ExtensionListener, InternalFilterAction {

    private Map<String, EntityFilterAdapter> filtersLookup = new HashMap<>();

    @Override
    public void onPostConstruct() {
        InternalActionManagerImpl actionManager = (InternalActionManagerImpl) ApplicationManager.getInstance()
                .getActionService();
        actionManager.addAction(InternalFilterAction.class, this);
    }

    @Override
    public void onPreDestroy() {
        InternalActionManagerImpl actionManager = (InternalActionManagerImpl) ApplicationManager.getInstance()
                .getActionService();
        actionManager.removeAction(InternalFilterAction.class, this);
    }

    @Override
    public void register(Extension extension) {
        if (extension.implementationClass() instanceof EntityFilterAdapter) {
            EntityFilterAdapter entityFilterAdapter = (EntityFilterAdapter) extension.implementationClass();
            filtersLookup.put(extension.extensionId(), entityFilterAdapter);
        }
    }

    @Override
    public void deregister(Extension extension) {
        if (extension.implementationClass() instanceof EntityFilterAdapter) {
            EntityFilterAdapter entityFilterAdapter = (EntityFilterAdapter) extension.implementationClass();
            filtersLookup.remove(extension.extensionId(), entityFilterAdapter);
        }
    }

    @Override
    public boolean filter(Entity entity, Map<String, String> matchedTextLookup, String fullText) {
        if (filtersLookup.isEmpty()) {
            return true;
        }
        return filtersLookup.keySet().stream().map(extensionId -> filtersLookup.get(extensionId)).filter(filter -> {
            String keywordName = filter.getKeywordName();
            return keywordName != null && !keywordName.isEmpty()
                    && !EntityFilterAdapter.DEFAULT_KEYWORDS.contains(keywordName)
                    && matchedTextLookup.containsKey(keywordName);
        }).allMatch(filter -> filter.onFilter(entity, matchedTextLookup.get(filter.getKeywordName()), fullText));
    }

    @Override
    public Map<String, EntityFilterAdapter> getFilterAdapters() {
        return Collections.unmodifiableMap(filtersLookup);
    }

    @Override
    public boolean hasFilters() {
        return !filtersLookup.isEmpty();
    }
}
