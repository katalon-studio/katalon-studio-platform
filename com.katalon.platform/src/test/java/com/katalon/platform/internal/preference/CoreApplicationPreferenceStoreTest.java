package com.katalon.platform.internal.preference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.prefs.BackingStoreException;

public class CoreApplicationPreferenceStoreTest {

    private String qualifier;

    private IEclipsePreferences instancePreferences;

    private IEclipsePreferences defaultPreferences;

    private CoreApplicationPreferenceStore store;

    @Before
    public void setUp() {
        qualifier = "com.katalon.platform.test." + UUID.randomUUID();
        instancePreferences = InstanceScope.INSTANCE.getNode(qualifier);
        defaultPreferences = DefaultScope.INSTANCE.getNode(qualifier);
        store = new CoreApplicationPreferenceStore(qualifier);
    }

    @After
    public void tearDown() throws BackingStoreException {
        instancePreferences = InstanceScope.INSTANCE.getNode(qualifier);
        defaultPreferences = DefaultScope.INSTANCE.getNode(qualifier);
        IEclipsePreferences instanceParent = (IEclipsePreferences) instancePreferences.parent();
        IEclipsePreferences defaultParent = (IEclipsePreferences) defaultPreferences.parent();
        instancePreferences.removeNode();
        defaultPreferences.removeNode();
        instanceParent.flush();
        defaultParent.flush();
    }

    @Test
    public void instanceValuesOverrideDefaults() {
        defaultPreferences.put("name", "default");
        assertEquals("default", store.getString("name"));

        store.setValue("name", "instance");
        assertEquals("instance", store.getString("name"));
    }

    @Test
    public void containsUsesInstanceThenDefaultAndRejectsNull() {
        assertFalse(store.contains(null));
        assertFalse(store.contains("name"));
        defaultPreferences.put("name", "default");
        assertTrue(store.contains("name"));
        instancePreferences.put("other", "instance");
        assertTrue(store.contains("other"));
    }

    @Test
    public void scalarWritesPreserveLegacyDefaultSemantics() {
        store.setValue("boolean", false);
        store.setValue("int", 0);
        store.setValue("string", "");

        assertNull(instancePreferences.get("boolean", null));
        assertNull(instancePreferences.get("int", null));
        assertNull(instancePreferences.get("string", null));
        assertTrue(store.needsSaving());

        defaultPreferences.putBoolean("boolean", true);
        defaultPreferences.putInt("int", 7);
        defaultPreferences.put("string", "default");
        store.setValue("boolean", false);
        store.setValue("int", 8);
        store.setValue("string", "override");
        store.setValue("boolean", true);
        store.setValue("int", 7);
        store.setValue("string", "default");

        assertNull(instancePreferences.get("boolean", null));
        assertNull(instancePreferences.get("int", null));
        assertNull(instancePreferences.get("string", null));
    }

    @Test
    public void saveFlushesAndClearsDirtyState() throws IOException {
        store.setValue("name", "value");
        assertTrue(store.needsSaving());

        store.save();

        assertFalse(store.needsSaving());
        assertEquals("value", instancePreferences.get("name", null));
    }

    @Test
    public void malformedIntegersUseLegacyZeroDefault() {
        instancePreferences.put("number", "not-a-number");
        assertEquals(0, store.getInt("number"));
    }

    @Test
    public void cachedStoreRecoversAfterItsInstanceNodeIsRemoved() throws Exception {
        store.setValue("name", "before");
        store.save();
        IEclipsePreferences parent = (IEclipsePreferences) instancePreferences.parent();
        instancePreferences.removeNode();
        parent.flush();

        store.setValue("name", "after");
        store.save();

        instancePreferences = InstanceScope.INSTANCE.getNode(qualifier);
        assertEquals("after", instancePreferences.get("name", null));
        assertEquals("after", store.getString("name"));
    }
}
