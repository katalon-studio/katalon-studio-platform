package com.katalon.platform.api.model.testobject;

public interface BasicSoapRequestEntity extends BasicWebServiceRequestEntity {
    String getSoapBodyContent();

    String getSoapServiceFunction();
}
