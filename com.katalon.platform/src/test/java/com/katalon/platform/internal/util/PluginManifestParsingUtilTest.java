package com.katalon.platform.internal.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.lifecycle.ExtensionConstants;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.api.service.ApplicationManager;
import com.katalon.platform.internal.ApplicationImpl;

public class PluginManifestParsingUtilTest {

    private static final String UI_EXTENSION_POINT = "com.katalon.platform.api.extension.newToolItem";

    private static final String RUNTIME_EXTENSION_POINT = "runtime.point";

    private String previousRuntime;

    private Application previousApplication;

    @Before
    public void setUp() {
        previousRuntime = System.getProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY);
        previousApplication = ApplicationManager.getInstance();
        ApplicationManager.setApplication(new ApplicationImpl());
    }

    @After
    public void tearDown() {
        if (previousRuntime == null) {
            System.clearProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY);
        } else {
            System.setProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY, previousRuntime);
        }
        ApplicationManager.setApplication(previousApplication);
    }

    @Test
    public void desktopRuntimeKeepsUiExtensionPoints() {
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable(null, UI_EXTENSION_POINT));
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable("desktop", UI_EXTENSION_POINT));
    }

    @Test
    public void headlessRuntimeKeepsOnlyNonUiExtensionPoints() {
        assertFalse(PluginManifestParsingUtil.isExtensionPointAvailable("headless", UI_EXTENSION_POINT));
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable("headless", RUNTIME_EXTENSION_POINT));
        assertTrue(PluginManifestParsingUtil.isExtensionPointAvailable("headless", null));
    }

    @Test
    public void headlessRuntimeDoesNotConstructUiExtensionPointListener() {
        System.setProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY, "headless");
        AtomicInteger constructions = new AtomicInteger();
        IConfigurationElement declaration = configuration(attributes(
                ExtensionConstants.ATTR_ID, UI_EXTENSION_POINT,
                ExtensionConstants.ATTR_INTERFACE_CLASS, "example.UiExtension",
                ExtensionConstants.ATTR_SERVICE_CLASS, "example.UiListener"), constructions, new ExtensionListener() { });
        Plugin plugin = PluginManifestParsingUtil.parsePlugin(bundle(),
                registry(new IExtension[] { extension(ExtensionConstants.EXTENSION_POINT_ID, declaration) },
                        new IConfigurationElement[] { declaration }));

        assertEquals(0, constructions.get());
        assertTrue(plugin.getExtensionPoints().isEmpty());
    }

    @Test
    public void desktopRuntimeConstructsUiExtensionPointListener() {
        System.clearProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY);
        AtomicInteger constructions = new AtomicInteger();
        IConfigurationElement declaration = configuration(attributes(
                ExtensionConstants.ATTR_ID, UI_EXTENSION_POINT,
                ExtensionConstants.ATTR_INTERFACE_CLASS, "example.UiExtension",
                ExtensionConstants.ATTR_SERVICE_CLASS, "example.UiListener"), constructions, new ExtensionListener() { });
        Plugin plugin = PluginManifestParsingUtil.parsePlugin(bundle(),
                registry(new IExtension[] { extension(ExtensionConstants.EXTENSION_POINT_ID, declaration) },
                        new IConfigurationElement[] { declaration }));

        assertEquals(1, constructions.get());
        assertEquals(1, plugin.getExtensionPoints().size());
    }

    @Test
    public void headlessRuntimeDoesNotConstructContributionForUiExtensionPoint() {
        System.setProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY, "headless");
        AtomicInteger constructions = new AtomicInteger();
        IConfigurationElement contribution = configuration(attributes(
                ExtensionConstants.ATTR_ID, "ui.extension",
                ExtensionConstants.ATTR_EXTENSION_POINT_ID, UI_EXTENSION_POINT,
                ExtensionConstants.ATTR_IMPLEMENTATION_CLASS, "example.UiExtension"),
                constructions, new Object());
        Plugin plugin = PluginManifestParsingUtil.parsePlugin(bundle(),
                registry(new IExtension[] { extension(ExtensionConstants.EXTENSION_ID, contribution) },
                        new IConfigurationElement[0]));

        assertEquals(0, constructions.get());
        assertTrue(plugin.getExtensions().isEmpty());
    }

    @Test
    public void headlessRuntimePreservesContributionWithUnknownProvider() {
        System.setProperty(PluginManifestParsingUtil.RUNTIME_PROPERTY, "headless");
        AtomicInteger constructions = new AtomicInteger();
        IConfigurationElement contribution = configuration(attributes(
                ExtensionConstants.ATTR_ID, "late.extension",
                ExtensionConstants.ATTR_EXTENSION_POINT_ID, "late.point",
                ExtensionConstants.ATTR_IMPLEMENTATION_CLASS, "example.LateExtension"),
                constructions, new Object());
        Plugin plugin = PluginManifestParsingUtil.parsePlugin(bundle(),
                registry(new IExtension[] { extension(ExtensionConstants.EXTENSION_ID, contribution) },
                        new IConfigurationElement[0]));

        assertEquals(1, constructions.get());
        assertEquals(1, plugin.getExtensions().size());
    }

    private static Bundle bundle() {
        return proxy(Bundle.class, (method, arguments) -> "getSymbolicName".equals(method) ? "test.plugin" : null);
    }

    private static IExtension extension(String extensionPointId, IConfigurationElement element) {
        return proxy(IExtension.class, (method, arguments) -> {
            if ("getExtensionPointUniqueIdentifier".equals(method)) {
                return extensionPointId;
            }
            if ("getConfigurationElements".equals(method)) {
                return new IConfigurationElement[] { element };
            }
            if ("getNamespaceIdentifier".equals(method)) {
                return "test.plugin";
            }
            return null;
        });
    }

    private static IConfigurationElement configuration(Map<String, String> attributes,
            AtomicInteger constructions, Object implementation) {
        return proxy(IConfigurationElement.class, (method, arguments) -> {
            if ("getAttribute".equals(method)) {
                return attributes.get((String) arguments[0]);
            }
            if ("createExecutableExtension".equals(method)) {
                constructions.incrementAndGet();
                return implementation;
            }
            return null;
        });
    }

    private static IExtensionRegistry registry(IExtension[] extensions, IConfigurationElement[] declarations) {
        return proxy(IExtensionRegistry.class, (method, arguments) -> {
            if ("getExtensions".equals(method)) {
                return extensions;
            }
            if ("getConfigurationElementsFor".equals(method)) {
                return declarations;
            }
            return null;
        });
    }

    private static Map<String, String> attributes(String... values) {
        Map<String, String> attributes = new HashMap<>();
        for (int index = 0; index < values.length; index += 2) {
            attributes.put(values[index], values[index + 1]);
        }
        return attributes;
    }

    @SuppressWarnings("unchecked")
    private static <T> T proxy(Class<T> type, ProxyInvocation invocation) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type },
                (proxy, method, arguments) -> invocation.invoke(method.getName(), arguments));
    }

    @FunctionalInterface
    private interface ProxyInvocation {
        Object invoke(String method, Object[] arguments) throws Throwable;
    }
}
