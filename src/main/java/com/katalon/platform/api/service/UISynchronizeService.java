package com.katalon.platform.api.service;

public interface UISynchronizeService {

    void syncExec(Runnable runnable);
    
    void asyncExec(Runnable runnable);
}
