package com.katalon.platform.api.ui;

import org.eclipse.swt.widgets.Shell;

import com.katalon.platform.api.exception.PlatformException;
import com.katalon.platform.api.model.FolderEntity;

public interface DialogService extends UIService {
    FolderEntity showTestCaseFolderSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;

    void openApplicationPreferences();

    void openPluginPreferencePage(String preferenceId);
}