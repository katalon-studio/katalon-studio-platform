package com.katalon.platform.api.network;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.preference.PluginPreference;
import com.katalon.platform.api.service.ApplicationManager;

public class NetworkPreference {

    private static final String NETWORK_PREFERENCE_ID = "com.kms.katalon.core.webservice";

    private final String projectId;

    private final PluginPreference pluginPreference;

    public NetworkPreference(String projectId) throws ResourceException {
        this.projectId = projectId;
        this.pluginPreference = ApplicationManager.getInstance()
                .getPreferenceManager()
                .getInternalPluginPreference(projectId, NETWORK_PREFERENCE_ID);
    }

    public static enum SSLCertificateOption {
        NONE, BYPASS
    }

    public interface NetworkPreferenceConstants {
        String SETTING_SSL_CERTIFICATE = "network.sslCert";
    }

    public SSLCertificateOption getSSLCertificateOption() {
        return SSLCertificateOption.valueOf(pluginPreference
                .getString(NetworkPreferenceConstants.SETTING_SSL_CERTIFICATE, SSLCertificateOption.BYPASS.name()));
    }

    public boolean isByPassSSLCertificate() {
        return getSSLCertificateOption() == SSLCertificateOption.BYPASS;
    }

    public String getProjectId() {
        return projectId;
    }
}
