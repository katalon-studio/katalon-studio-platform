package com.katalon.platform.internal.preference;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

final class CoreApplicationPreferenceStore {

    private static final boolean BOOLEAN_DEFAULT = false;

    private static final int INT_DEFAULT = 0;

    private static final String STRING_DEFAULT = "";

    private final String qualifier;

    private boolean dirty;

    CoreApplicationPreferenceStore(String qualifier) {
        this.qualifier = qualifier;
    }

    boolean contains(String name) {
        return name != null && internalGet(name) != null;
    }

    boolean getBoolean(String name) {
        String value = internalGet(name);
        return value == null ? BOOLEAN_DEFAULT : Boolean.valueOf(value).booleanValue();
    }

    int getInt(String name) {
        String value = internalGet(name);
        if (value == null) {
            return INT_DEFAULT;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return INT_DEFAULT;
        }
    }

    String getString(String name) {
        String value = internalGet(name);
        return value == null ? STRING_DEFAULT : value;
    }

    synchronized void setValue(String name, boolean value) {
        if (getBoolean(name) == value) {
            return;
        }
        if (defaultPreferences().getBoolean(name, BOOLEAN_DEFAULT) == value) {
            instancePreferences().remove(name);
        } else {
            instancePreferences().putBoolean(name, value);
        }
        dirty = true;
    }

    synchronized void setValue(String name, int value) {
        if (getInt(name) == value) {
            return;
        }
        if (defaultPreferences().getInt(name, INT_DEFAULT) == value) {
            instancePreferences().remove(name);
        } else {
            instancePreferences().putInt(name, value);
        }
        dirty = true;
    }

    synchronized void setValue(String name, String value) {
        if (defaultPreferences().get(name, STRING_DEFAULT).equals(value)) {
            instancePreferences().remove(name);
        } else {
            instancePreferences().put(name, value);
        }
        dirty = true;
    }

    synchronized boolean needsSaving() {
        return dirty;
    }

    synchronized void save() throws IOException {
        try {
            instancePreferences().flush();
            dirty = false;
        } catch (BackingStoreException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private String internalGet(String name) {
        String value = instancePreferences().get(name, null);
        return value == null ? defaultPreferences().get(name, null) : value;
    }

    private IEclipsePreferences instancePreferences() {
        return InstanceScope.INSTANCE.getNode(qualifier);
    }

    private IEclipsePreferences defaultPreferences() {
        return DefaultScope.INSTANCE.getNode(qualifier);
    }
}
