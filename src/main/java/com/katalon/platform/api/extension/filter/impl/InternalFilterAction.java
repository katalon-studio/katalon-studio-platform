package com.katalon.platform.api.extension.filter.impl;

import java.util.Map;

import com.katalon.platform.api.extension.filter.EntityFilterAdapter;
import com.katalon.platform.api.model.Entity;
import com.katalon.platform.api.service.InternalAction;

public interface InternalFilterAction extends InternalAction {
    boolean filter(Entity entity, Map<String, String> matchedTextLookup, String fullText);

    Map<String, EntityFilterAdapter> getFilterAdapters();
    
    boolean hasFilters();
}
