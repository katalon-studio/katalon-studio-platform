package com.katalon.platform.api.network;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.katalon.platform.api.preference.ApplicationPreference;
import com.katalon.platform.api.service.ApplicationManager;

public class ApplicationProxyPreference {
    
    public static final String PROXY_PREFERENCE_ID = "com.kms.katalon.execution";

    public interface ProxyPreferenceConstants {
        // Kept for backward compatible
        public static final String PROXY_OPTION = "proxy.option";

        public static final String PROXY_SERVER_TYPE = "proxy.server.type";

        public static final String PROXY_SERVER_ADDRESS = "proxy.server.address";

        public static final String PROXY_SERVER_PORT = "proxy.server.port";

        public static final String PROXY_USERNAME = "proxy.username";

        public static final String PROXY_PASSWORD = "proxy.password";

        public static final String PROXY_EXCEPTION_LIST = "proxy.excludes";

        public static final String PROXY_PREFERENCE_SET = "proxy.preferences.set";

        // Authentication Proxy
        public static final String AUTH_PROXY_OPTION = "proxy.auth.option";

        public static final String AUTH_PROXY_SERVER_TYPE = "proxy.auth.server.type";

        public static final String AUTH_PROXY_SERVER_ADDRESS = "proxy.auth.server.address";

        public static final String AUTH_PROXY_SERVER_PORT = "proxy.auth.server.port";

        public static final String AUTH_PROXY_USERNAME = "proxy.auth.username";

        public static final String AUTH_PROXY_PASSWORD = "proxy.auth.password";

        public static final String AUTH_PROXY_EXCEPTION_LIST = "proxy.auth.excludes";

        public static final String AUTH_PROXY_PREFERENCE_SET = "proxy.auth.preferences.set";

        // System Proxy
        public static final String SYSTEM_PROXY_OPTION = "proxy.system.option";

        public static final String SYSTEM_PROXY_SERVER_TYPE = "proxy.system.server.type";

        public static final String SYSTEM_PROXY_SERVER_ADDRESS = "proxy.system.server.address";

        public static final String SYSTEM_PROXY_SERVER_PORT = "proxy.system.server.port";

        public static final String SYSTEM_PROXY_USERNAME = "proxy.system.username";

        public static final String SYSTEM_PROXY_PASSWORD = "proxy.system.password";

        public static final String SYSTEM_PROXY_EXCEPTION_LIST = "proxy.system.excludes";

        public static final String SYSTEM_PROXY_APPLY_TO_DESIRED_CAPABILITIES = "proxy.system.applyToDesiredCapabilities";

        public static final String SYSTEM_PROXY_PREFERENCE_SET = "proxy.system.preferences.set";
    }

    @Deprecated
    public static boolean isProxyPreferencesSet() {
        ApplicationPreference store = getPreferenceStore();
        return store.getBoolean(ProxyPreferenceConstants.PROXY_PREFERENCE_SET, false);
    }

    @Deprecated
    public static ProxyInformation getProxyInformation() {
        ApplicationPreference store = getPreferenceStore();
        ProxyInformation proxyInfo = new ProxyInformation();
        proxyInfo.setProxyOption(StringUtils.defaultIfEmpty(store.getString(ProxyPreferenceConstants.PROXY_OPTION, StringUtils.EMPTY),
                ProxyOption.NO_PROXY.name()));
        proxyInfo.setProxyServerType(StringUtils.defaultIfEmpty(
                store.getString(ProxyPreferenceConstants.PROXY_SERVER_TYPE, StringUtils.EMPTY), ProxyServerType.HTTP.name()));
        proxyInfo.setProxyServerAddress(store.getString(ProxyPreferenceConstants.PROXY_SERVER_ADDRESS, StringUtils.EMPTY));
        proxyInfo.setProxyServerPort(store.getInt(ProxyPreferenceConstants.PROXY_SERVER_PORT, 0));
        proxyInfo.setUsername(store.getString(ProxyPreferenceConstants.PROXY_USERNAME, StringUtils.EMPTY));
        proxyInfo.setPassword(store.getString(ProxyPreferenceConstants.PROXY_PASSWORD, StringUtils.EMPTY));
        proxyInfo.setExceptionList(store.getString(ProxyPreferenceConstants.PROXY_EXCEPTION_LIST, StringUtils.EMPTY));
        return proxyInfo;
    }

    public static boolean isAuthProxyPreferencesSet() {
        ApplicationPreference store = getPreferenceStore();
        return store.getBoolean(ProxyPreferenceConstants.AUTH_PROXY_PREFERENCE_SET, false);
    }

    public static ProxyInformation getAuthProxyInformation() {
        if (!isAuthProxyPreferencesSet()) {
            return getProxyInformation();
        }

        ApplicationPreference store = getPreferenceStore();
        ProxyInformation proxyInfo = new ProxyInformation();
        proxyInfo.setProxyOption(StringUtils.defaultIfEmpty(store.getString(ProxyPreferenceConstants.AUTH_PROXY_OPTION, StringUtils.EMPTY),
                ProxyOption.NO_PROXY.name()));
        proxyInfo.setProxyServerType(StringUtils.defaultIfEmpty(
                store.getString(ProxyPreferenceConstants.AUTH_PROXY_SERVER_TYPE, StringUtils.EMPTY), ProxyServerType.HTTP.name()));
        proxyInfo.setProxyServerAddress(store.getString(ProxyPreferenceConstants.AUTH_PROXY_SERVER_ADDRESS, StringUtils.EMPTY));
        proxyInfo.setProxyServerPort(store.getInt(ProxyPreferenceConstants.AUTH_PROXY_SERVER_PORT, 0));
        proxyInfo.setUsername(store.getString(ProxyPreferenceConstants.AUTH_PROXY_USERNAME, StringUtils.EMPTY));
        proxyInfo.setPassword(store.getString(ProxyPreferenceConstants.AUTH_PROXY_PASSWORD, StringUtils.EMPTY));
        proxyInfo.setExceptionList(store.getString(ProxyPreferenceConstants.AUTH_PROXY_EXCEPTION_LIST, StringUtils.EMPTY));
        return proxyInfo;
    }

    public static boolean isSystemProxyPreferencesSet() {
        ApplicationPreference store = getPreferenceStore();
        return store.getBoolean(ProxyPreferenceConstants.SYSTEM_PROXY_PREFERENCE_SET, false);
    }

    public static ProxyInformation getSystemProxyInformation() {
        if (!isSystemProxyPreferencesSet()) {
            ProxyInformation proxyInfo = getProxyInformation();
            proxyInfo.setApplyToDesiredCapabilities(true);
            return proxyInfo;
        }

        ApplicationPreference store = getPreferenceStore();
        ProxyInformation proxyInfo = new ProxyInformation();
        proxyInfo.setProxyOption(StringUtils.defaultIfEmpty(
                store.getString(ProxyPreferenceConstants.SYSTEM_PROXY_OPTION, StringUtils.EMPTY), ProxyOption.NO_PROXY.name()));
        proxyInfo.setProxyServerType(StringUtils.defaultIfEmpty(
                store.getString(ProxyPreferenceConstants.SYSTEM_PROXY_SERVER_TYPE, StringUtils.EMPTY), ProxyServerType.HTTP.name()));
        proxyInfo.setProxyServerAddress(store.getString(ProxyPreferenceConstants.SYSTEM_PROXY_SERVER_ADDRESS, StringUtils.EMPTY));
        proxyInfo.setProxyServerPort(store.getInt(ProxyPreferenceConstants.SYSTEM_PROXY_SERVER_PORT, 0));
        proxyInfo.setUsername(store.getString(ProxyPreferenceConstants.SYSTEM_PROXY_USERNAME, StringUtils.EMPTY));
        proxyInfo.setPassword(store.getString(ProxyPreferenceConstants.SYSTEM_PROXY_PASSWORD, StringUtils.EMPTY));
        proxyInfo.setExceptionList(store.getString(ProxyPreferenceConstants.SYSTEM_PROXY_EXCEPTION_LIST, StringUtils.EMPTY));
        proxyInfo.setApplyToDesiredCapabilities(
                store.getBoolean(ProxyPreferenceConstants.SYSTEM_PROXY_APPLY_TO_DESIRED_CAPABILITIES, false));
        return proxyInfo;
    }

    private static ApplicationPreference getPreferenceStore() {
        return ApplicationManager.getInstance().getPreferenceManager().getApplicationPreference(PROXY_PREFERENCE_ID);
    }

    public static class ProxyInformation {
        private String proxyOption;

        private String proxyServerType;

        private String username;

        private String password;

        private String proxyServerAddress;
        
        private int proxyServerPort;
        
        private String exceptionList;
        
        private boolean applyToDesiredCapabilities;
        
        public String getExceptionList() {
            return exceptionList;
        }

        public void setExceptionList(String exceptionList) {
            this.exceptionList = StringUtils.isNotEmpty(exceptionList) ? exceptionList : "";
        }

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
                this.proxyServerPort = Integer.parseInt(StringUtils.isNotEmpty(proxyServerPort) ? proxyServerPort : "-1");
            } catch (NumberFormatException ex) {
                this.proxyServerPort = -1;
            }
        }

        public boolean isApplyToDesiredCapabilities() {
            return applyToDesiredCapabilities;
        }

        public void setApplyToDesiredCapabilities(boolean applyToDesiredCapabilities) {
            this.applyToDesiredCapabilities = applyToDesiredCapabilities;
        }

        @Override
        public String toString() {
            return "ProxyInformation { "
                    + "proxyOption=" + proxyOption + ", "
                    + "proxyServerType=" + proxyServerType + ", "
                    + "username=" + username + ", "
                    + "password=" + "********" + ", "
                    + "proxyServerAddress=" + proxyServerAddress + ", "
                    + "proxyServerPort=" + proxyServerPort + ", "
                    + "executionList=\"" + exceptionList + "\", "
                    + "isApplyToDesiredCapabilities=" + applyToDesiredCapabilities
                    + " }";
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
