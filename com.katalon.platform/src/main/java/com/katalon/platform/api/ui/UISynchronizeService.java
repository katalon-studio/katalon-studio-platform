package com.katalon.platform.api.ui;

public interface UISynchronizeService extends UIService {
    void syncExec(Runnable runnable);

    void asyncExec(Runnable runnable);
}