package com.katalon.platform.api.model.testobject;

import java.util.List;

import com.katalon.platform.api.model.VariableEntity;

public interface BasicWebServiceRequestEntity {

    String SOAP = "SOAP";

    String SOAP12 = "SOAP12";

    String RESTFUL = "RESTful";

    String REST = "REST";

    String GET_METHOD = "GET";

    String POST_METHOD = "POST";

    String PUT_METHOD = "PUT";

    String PATCH_METHOD = "PATCH";

    String DELETE_METHOD = "DELETE";

    String HEAD_METHOD = "HEAD";

    String CONNECT_METHOD = "CONNECT";

    String OPTIONS_METHOD = "OPTIONS";

    String TRACE_METHOD = "TRACE";

    String[] SERVICE_TYPES = new String[] { SOAP, RESTFUL };

    String[] SOAP_REQUEST_METHODS = new String[] { SOAP, SOAP12, GET_METHOD, POST_METHOD };

    String[] REST_REQUEST_METHODS = new String[] { GET_METHOD, POST_METHOD, PUT_METHOD, PATCH_METHOD, DELETE_METHOD,
            HEAD_METHOD, CONNECT_METHOD, OPTIONS_METHOD, TRACE_METHOD };
    
    String getDescription();

    String getServiceType(); // SOAP, Restful

    String getRequestUrl();

    List<WebServiceProperty> getHttpHeaders();

    List<WebServiceProperty> getRequestParameters();

    String getRequestMethod();

    String getVerificationScript();

    List<VariableEntity> getVariables();
}
