package com.katalon.platform.internal.util;

import org.apache.commons.text.StringEscapeUtils;

public class PropertySettingStoreUtil {

    private static final String BOOLEAN_REGEX = "^(true|false)$";

    private static final String INTEGER_REGEX = "^(-)?\\d+$";

    private static final String STRING_REGEX = "^\".*\"$";

    private static final String PROPERTY_NAME_REGEX = "^[a-zA-Z0-9\\.\\-_@\\*]+$";

    public static Object getValue(String rawValue) {
        if (rawValue == null || rawValue.isEmpty())
            return null;

        if (rawValue.matches(BOOLEAN_REGEX)) {
            return Boolean.valueOf(rawValue);
        } else if (rawValue.matches(INTEGER_REGEX)) {
            return Integer.valueOf(rawValue);
        } else if (rawValue.matches(STRING_REGEX)) {
            return StringEscapeUtils.unescapeJava(rawValue.substring(1, rawValue.length() - 1));
        } else {
            return rawValue;
        }
    }

    public static String getRawValue(Object value) {
        if (value == null)
            return null;
        if (value instanceof String) {
            return "\"" + StringEscapeUtils.escapeJava((String) value) + "\"";
        } else {
            return String.valueOf(value);
        }
    }

    public static boolean isValidPropertyName(String name) {
        if (name == null || name.isEmpty())
            return false;
        return name.matches(PROPERTY_NAME_REGEX);
    }
}
