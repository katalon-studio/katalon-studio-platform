package com.katalon.platform.api.model.testobject;

public interface BasicRestRequestEntity extends BasicWebServiceRequestEntity {

    String getHttpBodyType(); // text, x-www-form-urlencoded, form-data, file

    String getHttpBodyContent(); // JSON format of body content
}
