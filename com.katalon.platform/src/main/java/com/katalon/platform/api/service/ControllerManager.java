package com.katalon.platform.api.service;

import com.katalon.platform.api.controller.Controller;

public interface ControllerManager {
    <T extends Controller> T getController(Class<T> clazz); 
}