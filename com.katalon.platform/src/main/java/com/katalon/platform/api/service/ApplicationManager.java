package com.katalon.platform.api.service;

import com.katalon.platform.api.Application;

public class ApplicationManager {
    private static Application instance;

    public static void setApplication(Application application) {
        instance = application;
    }

    public static Application getInstance() {
        return instance;
    }
}
