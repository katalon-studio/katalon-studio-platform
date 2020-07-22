package com.katalon.platform.api.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.ssl.KeyMaterial;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.katalon.platform.api.network.ApplicationProxyPreference.ProxyInformation;
import com.katalon.platform.api.network.ApplicationProxyPreference.ProxyOption;
import com.katalon.platform.api.network.DefaultKeepAliveStrategy;
import com.katalon.platform.api.network.DefaultRedirectStrategy;
import com.katalon.platform.api.network.NetworkPreference.SSLCertificateOption;
import com.katalon.platform.api.network.SSLClientCertificateSettings;
import com.katalon.platform.api.network.WebServiceProxyRoutePlanner;

public class HttpUtil {

    private static final String TLS = "TLS";

    private static PoolingHttpClientConnectionManager connectionManager;

    private static final String SOCKET_FACTORY_REGISTRY = "http.socket-factory-registry";

    static {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setValidateAfterInactivity(1);
        connectionManager.setMaxTotal(2000);
        connectionManager.setDefaultMaxPerRoute(500);
    }

    public static HttpResponse sendRequest(HttpUriRequest request) throws KeyManagementException, MalformedURLException,
            URISyntaxException, IOException, GeneralSecurityException {
        return sendRequest(request, true, null, SSLCertificateOption.BYPASS, null);
    }

    public static HttpResponse sendRequest(HttpUriRequest request, ProxyInformation proxyInformation) throws KeyManagementException, MalformedURLException,
            URISyntaxException, IOException, GeneralSecurityException {
        return sendRequest(request, true, proxyInformation, SSLCertificateOption.BYPASS, null);
    }

    public static HttpResponse sendRequest(HttpUriRequest request, boolean followRedirects,
            ProxyInformation proxyInformation, SSLCertificateOption certificateOption,
            SSLClientCertificateSettings clientCertSettings) throws MalformedURLException, URISyntaxException,
            IOException, KeyManagementException, GeneralSecurityException {

        String url = request.getURI().toURL().toString();
        HttpClientBuilder clientBuilder = getClientBuilder(url, followRedirects, proxyInformation);
        CloseableHttpClient httpClient = clientBuilder.build();
        HttpContext httpContext = getDefaultHttpContext(certificateOption, clientCertSettings);
        HttpResponse response = httpClient.execute(request, httpContext);
        return response;
    }

    private static HttpClientBuilder getClientBuilder(String url, boolean followRedirects,
            ProxyInformation proxyInformation) throws MalformedURLException, URISyntaxException, IOException {

        HttpClientBuilder clientBuilder = HttpClients.custom();

        if (followRedirects) {
            clientBuilder.setRedirectStrategy(new DefaultRedirectStrategy());
        } else {
            clientBuilder.disableRedirectHandling();
        }

        clientBuilder.setConnectionManager(connectionManager);
        clientBuilder.setConnectionManagerShared(true);

        configureProxy(clientBuilder, url, proxyInformation);

        clientBuilder.setKeepAliveStrategy(new DefaultKeepAliveStrategy());

        return clientBuilder;
    }

    private static void configureProxy(HttpClientBuilder clientBuilder, String url, ProxyInformation proxyInformation)
            throws MalformedURLException, URISyntaxException, IOException {

        if (proxyInformation == null) {
            return;
        }

        if (ProxyOption.valueOf(proxyInformation.getProxyOption()).equals(ProxyOption.NO_PROXY)) {
            return;
        }

        clientBuilder.setRoutePlanner(new WebServiceProxyRoutePlanner(proxyInformation));

        HttpHost httpProxy = new HttpHost(proxyInformation.getProxyServerAddress(),
                proxyInformation.getProxyServerPort());

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        String proxyUsername = proxyInformation.getUsername();
        String proxyPassword = proxyInformation.getPassword();
        if (StringUtils.isNotBlank(proxyUsername) && StringUtils.isNotBlank(proxyPassword)) {
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword);
            credentialsProvider.setCredentials(new AuthScope(httpProxy), credentials);
        }

        clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }

    private static HttpContext getDefaultHttpContext(SSLCertificateOption certificateOption,
            SSLClientCertificateSettings clientCertSettings)
            throws KeyManagementException, GeneralSecurityException, IOException {

        HttpContext httpContext = new BasicHttpContext();
        SSLContext sc = SSLContext.getInstance(TLS);
        sc.init(getKeyManagers(clientCertSettings), getTrustManagers(certificateOption), null);
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sc, getHostnameVerifier(certificateOption))).build();
        httpContext.setAttribute(SOCKET_FACTORY_REGISTRY, reg);
        return httpContext;
    }

    private static TrustManager[] getTrustManagers(SSLCertificateOption certificateOption) throws IOException {
        if (certificateOption == SSLCertificateOption.BYPASS) {
            return new TrustManager[] { new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            } };
        }
        return new TrustManager[0];
    }

    private static KeyManager[] getKeyManagers(SSLClientCertificateSettings clientCertSettings)
            throws GeneralSecurityException, IOException {
        if (clientCertSettings == null) {
            return new KeyManager[0];
        }

        String keyStoreFilePath = clientCertSettings.getKeyStoreFile();
        if (!StringUtils.isBlank(keyStoreFilePath)) {
            File keyStoreFile = new File(keyStoreFilePath);
            String keyStorePassword = !StringUtils.isBlank(clientCertSettings.getKeyStorePassword())
                    ? clientCertSettings.getKeyStorePassword() : StringUtils.EMPTY;
            if (keyStoreFile.exists()) {
                KeyManagerFactory keyManagerFactory = KeyManagerFactory
                        .getInstance(KeyManagerFactory.getDefaultAlgorithm());
                KeyMaterial km = new KeyMaterial(keyStoreFile, keyStorePassword.toCharArray());
                keyManagerFactory.init(km.getKeyStore(), keyStorePassword.toCharArray());
                return keyManagerFactory.getKeyManagers();
            }
        }
        return new KeyManager[0];
    }

    private static HostnameVerifier getHostnameVerifier(SSLCertificateOption certificateOption) {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                return certificateOption == SSLCertificateOption.BYPASS;
            }
        };
    }
}
