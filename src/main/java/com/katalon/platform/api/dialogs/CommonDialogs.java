package com.katalon.platform.api.dialogs;

import org.eclipse.swt.widgets.Shell;

import com.katalon.platform.api.PlatformException;
import com.katalon.platform.api.model.Folder;

public interface CommonDialogs {
    
    Folder showTestCaseFolderSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;
}
