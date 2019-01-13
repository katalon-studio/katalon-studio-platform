package com.katalon.platform.api;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public interface PluginInstaller {
    Bundle installPlugin(BundleContext bundleContext, String fileUrl) throws BundleException;

    Bundle uninstallPlugin(BundleContext bundleContext, String fileUrl) throws BundleException;
}
