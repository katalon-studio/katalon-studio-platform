package com.katalon.platform.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface RequestController extends Controller {

    /**
     * Send a HTTP request to the target server.
     * 
     * @param request The HTTP request to send.
     * @return the response from the server.
     */
    public HttpResponse send(HttpUriRequest request) throws URISyntaxException, IOException, GeneralSecurityException;

    /**
     * Send a HTTP request to the target server.
     * 
     * @param request The HTTP request to send.
     * @return the response from the server.
     */
    public HttpResponse sendWithProxy(HttpUriRequest request)
            throws URISyntaxException, IOException, GeneralSecurityException;
}
