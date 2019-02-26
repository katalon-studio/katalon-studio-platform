package com.katalon.platform.api.network;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.katalon.platform.api.preference.ApplicationPreference;
import com.katalon.platform.api.service.ApplicationManager;

public class ApplicationProxyPreference {
    
    public static final String PROXY_PREFERENCE_ID = "com.kms.katalon.execution";

    public interface ProxyPreferenceConstants {
        String PROXY_OPTION = "proxy.option";

        String PROXY_SERVER_TYPE = "proxy.server.type";

        String PROXY_SERVER_ADDRESS = "proxy.server.address";

        String PROXY_SERVER_PORT = "proxy.server.port";

        String PROXY_USERNAME = "proxy.username";

        String PROXY_PASSWORD = "proxy.password";

        String PROXY_PREFERENCE_SET = "proxy.preferences.set";

        String DISABLE_MOB_BROWSER_PROXY = "proxy.preferences.disableMobRowserProxy";
    }

    public static boolean isProxyPreferencesSet() {
        ApplicationPreference store = getPreferenceStore();
        return store.getBoolean(ProxyPreferenceConstants.PROXY_PREFERENCE_SET, false);
    }

    private static ApplicationPreference getPreferenceStore() {
        return ApplicationManager.getInstance().getPreferenceManager().getApplicationPreference(PROXY_PREFERENCE_ID);
    }

    public static ProxyInformation getProxyInformation() {
        ApplicationPreference store = getPreferenceStore();
        ProxyInformation proxyInfo = new ProxyInformation();
        proxyInfo.setProxyOption(store.getString(ProxyPreferenceConstants.PROXY_OPTION, ProxyOption.NO_PROXY.name()));
        proxyInfo.setProxyServerType(
                store.getString(ProxyPreferenceConstants.PROXY_SERVER_TYPE, ProxyServerType.HTTP.name()));
        proxyInfo.setProxyServerAddress(store.getString(ProxyPreferenceConstants.PROXY_SERVER_ADDRESS, ""));
        proxyInfo.setProxyServerPort(store.getInt(ProxyPreferenceConstants.PROXY_SERVER_PORT, 0));
        proxyInfo.setUsername(store.getString(ProxyPreferenceConstants.PROXY_USERNAME, ""));
        proxyInfo.setPassword(store.getString(ProxyPreferenceConstants.PROXY_PASSWORD, ""));
        proxyInfo.setDisableMobBrowserProxy(store.getBoolean(ProxyPreferenceConstants.DISABLE_MOB_BROWSER_PROXY, false));
        return proxyInfo;
    }

    public static class ProxyInformation {
        private String proxyOption;

        private String proxyServerType;

        private String username;

        private String password;

        private String proxyServerAddress;

        private boolean disableMobBrowserProxy;

        private int proxyServerPort;

        public String getProxyOption() {
            return proxyOption;
        }

        public void setProxyOption(String proxyOption) {
            if (StringUtils.isEmpty(proxyOption)) {
                proxyOption = ProxyOption.NO_PROXY.name();
            }
            this.proxyOption = proxyOption;
        }

        public String getProxyServerType() {
            return proxyServerType;
        }

        public void setProxyServerType(String proxyServerType) {
            this.proxyServerType = StringUtils.isNotEmpty(proxyServerType) ? proxyServerType : "";
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = StringUtils.isNotEmpty(username) ? username : "";
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = StringUtils.isNotEmpty(password) ? password : "";
        }

        public String getProxyServerAddress() {
            return proxyServerAddress;
        }

        public void setProxyServerAddress(String proxyServerAddress) {
            this.proxyServerAddress = StringUtils.isNotEmpty(proxyServerAddress) ? proxyServerAddress : "";
        }

        public int getProxyServerPort() {
            return proxyServerPort;
        }

        public void setProxyServerPort(int proxyServerPort) {
            this.proxyServerPort = proxyServerPort;
        }

        public void setProxyServerPort(String proxyServerPort) {
            try {
                this.proxyServerPort = Integer
                        .parseInt(StringUtils.isNotEmpty(proxyServerPort) ? proxyServerPort : "-1");
            } catch (NumberFormatException ex) {
                this.proxyServerPort = -1;
            }
        }

        @Override
        public String toString() {
            return "ProxyInformation{" + "proxyOption=" + proxyOption + ", " + "proxyServerType=" + proxyServerType
                    + ", " + "password=" + password + ", " + "proxyServerAddress=" + proxyServerAddress + ", "
                    + "proxyServerPort=" + proxyServerPort + "}";
        }

        public void setDisableMobBrowserProxy(boolean boolean1) {
            this.disableMobBrowserProxy = boolean1;
        }

        public boolean getDisableMobBroserProxy() {
            return disableMobBrowserProxy;
        }
    }

    public static enum ProxyOption {
        NO_PROXY("No Proxy"), USE_SYSTEM("Use system proxy configuration"), MANUAL_CONFIG("Manual proxy configuration");

        private String displayName;

        private ProxyOption(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static String[] stringValues() {
            ProxyOption[] values = values();
            return Arrays.asList(values)
                    .stream()
                    .map(proxyServerType -> proxyServerType.toString())
                    .collect(Collectors.toList())
                    .toArray(new String[values.length]);
        }

        public static String[] displayStringValues() {
            ProxyOption[] values = values();
            return Arrays.asList(values)
                    .stream()
                    .map(proxyServerType -> proxyServerType.getDisplayName())
                    .collect(Collectors.toList())
                    .toArray(new String[values.length]);
        }

        public static ProxyOption valueOfDisplayName(String displayName) {
            return Arrays.asList(values())
                    .parallelStream()
                    .filter(proxyOption -> proxyOption.getDisplayName().equals(displayName))
                    .findAny()
                    .orElse(null);
        }
    }

    public static enum ProxyServerType {
        HTTP, HTTPS, SOCKS;

        public static String[] stringValues() {
            ProxyServerType[] values = values();
            return Arrays.asList(values)
                    .stream()
                    .map(proxyServerType -> proxyServerType.toString())
                    .collect(Collectors.toList())
                    .toArray(new String[values.length]);
        }
    }

}
