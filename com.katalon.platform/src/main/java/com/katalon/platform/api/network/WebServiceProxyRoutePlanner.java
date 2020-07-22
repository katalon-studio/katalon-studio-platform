package com.katalon.platform.api.network;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.protocol.HttpContext;

import com.katalon.platform.api.network.ApplicationProxyPreference.ProxyInformation;
import com.katalon.platform.api.network.ApplicationProxyPreference.ProxyOption;
import com.katalon.platform.api.util.ProxyUtils;


public class WebServiceProxyRoutePlanner implements HttpRoutePlanner {
    private ProxyInformation proxyInformation;

    public WebServiceProxyRoutePlanner(ProxyInformation proxyInformation) {
        this.proxyInformation = proxyInformation;
    }

    @Override
    public HttpRoute determineRoute(HttpHost host, HttpRequest request, HttpContext context) throws HttpException {
        if (proxyInformation == null) {
            return null;
        }

        if (ProxyOption.valueOf(proxyInformation.getProxyOption()).equals(ProxyOption.NO_PROXY)) {
            return null;
        }

        HttpHost httpProxy = new HttpHost(proxyInformation.getProxyServerAddress(),
                proxyInformation.getProxyServerPort());
        if ((ProxyOption.valueOf(proxyInformation.getProxyOption()).equals(ProxyOption.USE_SYSTEM))) {
            return new SystemDefaultRoutePlanner(ProxyUtils.getAutoProxySelector()).determineRoute(host, request,
                    context);
        } else {
            return new DefaultProxyRoutePlanner(httpProxy).determineRoute(host, request, context);
        }
    }

}
