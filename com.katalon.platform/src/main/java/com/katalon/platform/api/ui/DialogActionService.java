package com.katalon.platform.api.ui;

import org.eclipse.swt.widgets.Shell;

import com.katalon.platform.api.exception.PlatformException;
import com.katalon.platform.api.model.FolderEntity;

/**
 * UISynchronizeService is a unique KS UIService to help KS plugins can use KS dialog built-in functions.
 * 
 * @see #showTestCaseFolderSelectionDialog(Shell, String)
 * @see #openApplicationPreferences()
 * @see #openPluginPreferencePage(String)
 * 
 * @since 1.0.4
 */
public interface DialogActionService extends UIService {
    /**
     * Opens the test case folder selection and returns the selected folder after users press OK button.
     *
     * @param parentShell parent shell of the dialog.
     * @param dialogTitle title of the dialog
     * @return the selected folder
     * @throws PlatformException thrown if KS could not initialize dialog and return the selected folder
     * 
     * @since 1.0.4
     */
    FolderEntity showTestCaseFolderSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;

    /**
     * Opens KS preferences dialog
     * 
     * @since 1.0.4
     */
    void openApplicationPreferences();

    /**
     * Opens Preference settings page of a installed plugin under <code>Plugins</code> of <code>Project Settings</code>.
     * 
     * @param preferenceId id of the default preference page. Can be null or empty.
     * 
     * @since 1.0.4
     */
    void openPluginPreferencePage(String preferenceId);
}
