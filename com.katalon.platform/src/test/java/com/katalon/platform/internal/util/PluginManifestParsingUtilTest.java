package com.katalon.platform.internal.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PluginManifestParsingUtilTest {

    @Test
    public void desktopRuntimeKeepsUiExtensionPoints() {
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable(null, "true"));
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable("desktop", "true"));
    }

    @Test
    public void headlessRuntimeKeepsOnlyNonUiExtensionPoints() {
        assertFalse(PluginManifestParsingUtil.isExtensionPointAvailable("headless", "true"));
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable("headless", "false"));
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable("headless", null));
    }
}
