package com.katalon.platform.api.extension.filter;

import java.util.Arrays;
import java.util.List;

import com.katalon.platform.api.model.Entity;

public interface EntityFilterAdapter {
    String ENTITY_FILTER_ADAPTER_EXTENSION_POINT_ID = "com.katalon.platform.api.extension.filter.entityFilterAdapter";

    List<String> DEFAULT_KEYWORDS = Arrays.asList("id", "name", "tag", "comment", "description", "source name");

    /**
     * The additional unique keyword is used to combine with others to filter all entities in the Tests
     * Explorer.
     * </p>
     * Default Katalon's built-in keywords: <b>id, name, tag, comment, description, source name</b>.
     * External plugins should not re-define these keywords.
     * 
     */
    String getKeywordName();

    /**
     * This method will be invoked if the fullText as the search text contains the {@link #getKeywordName()}.</br>
     * Returns true means the given entity will be displayed in Tests Explorer, false means the entity will be hidden.
     * 
     * @param entity
     * entity to filter
     * @param matchedText
     * the text is in the keywordName clause
     * @param fullText
     * full text of the search box
     * @return <code>true</code> if the given entity matches with the matchedText, or allText. Otherwise,
     * <code>false</code>.
     */
    boolean onFilter(Entity entity, String matchedText, String fullText);
}
