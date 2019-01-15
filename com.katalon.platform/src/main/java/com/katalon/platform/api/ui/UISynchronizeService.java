package com.katalon.platform.api.ui;

/**
 * UISynchronizeService is a unique KS UIService to help KS plugins can synchronize, and synchronize their code with KS
 * main UI thread.
 * 
 * @see #syncExec(Runnable)
 * @see #asyncExec(Runnable)
 * 
 * @since 1.0.4
 */
public interface UISynchronizeService extends UIService {
    /**
     * Synchronizes the given <code>runnable</code> with main UI thread.
     * 
     * @param runnable the runnable code
     * @since 1.0.4
     */
    void syncExec(Runnable runnable);

    /**
     * Asynchronizes the given <code>runnable</code> with main UI thread.
     * 
     * @param runnable the runnable code
     * @since 1.0.4
     */
    void asyncExec(Runnable runnable);
}
