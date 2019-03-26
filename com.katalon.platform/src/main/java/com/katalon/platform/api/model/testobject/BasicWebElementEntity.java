package com.katalon.platform.api.model.testobject;

import java.util.List;
import java.util.Map;

public interface BasicWebElementEntity {
    
    String getDescription();

    boolean hasParentElement();

    String getParentElementId();

    List<WebElementProperty> getWebElementProperties();

    List<WebElementProperty> getXpathElementProperties();

    String getImagePath();

    boolean isRelativeImagePath();

    WebElementSelectorMethod getSelectorMethod();

    Map<WebElementSelectorMethod, String> getSelectorCollection();
}