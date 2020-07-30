package com.katalon.platform.api.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface RequestController extends Controller {

    /**
     * Send an HTTP request to a specific server.
     * 
     * @param request The HTTP request to send.
     * @return the response from the server.
     */
    public HttpResponse send(HttpUriRequest request) throws KeyManagementException, MalformedURLException,
            URISyntaxException, IOException, GeneralSecurityException;

    /**
     * Send an HTTP request to a specific server.
     * 
     * @param request The HTTP request to send.
     * @return the response from the server.
     */
    public HttpResponse sendWithProxy(HttpUriRequest request)
            throws KeyManagementException, MalformedURLException, URISyntaxException, IOException,
            GeneralSecurityException;
}
