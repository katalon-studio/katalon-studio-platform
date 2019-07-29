package com.katalon.platform.api.ui;

import org.eclipse.swt.widgets.Shell;

import com.katalon.platform.api.exception.PlatformException;
import com.katalon.platform.api.model.ExecutionProfileEntity;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.TestCaseEntity;
import com.katalon.platform.api.model.TestObjectEntity;

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
     * Opens the test object folder selection and returns the selected folder after users press OK button.
     * 
     * @param parentShell parent shell of the dialog.
     * @param dialogTitle title of the dialog
     * @return the selected folder
     * @throws PlatformException thrown if KS could not initialize dialog and return the selected folder
     * 
     * @since 1.0.12
     */
    FolderEntity showTestObjectFolderSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;

    /**
     * Opens the test case selection and returns the list of selected test cases after users press OK button.
     * 
     * @param parentShell parent shell of the dialog.
     * @param dialogTitle title of the dialog.
     * @return an array containing selected test cases.
     * @throws PlatformException thrown if KS could not initialize dialog and return the selected test cases
     * 
     * @since 1.0.12
     */
    TestCaseEntity[] showTestCaseSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;

    /**
     * Opens the test object selection and returns the list of selected test objects after users press OK button.
     * 
     * @param parentShell parent shell of the dialog.
     * @param dialogTitle title of the dialog.
     * @return an array containing selected test objects.
     * @throws PlatformException thrown if KS could not initialize dialog and return the selected test objects
     * 
     * @since 1.0.12
     */
    TestObjectEntity[] showTestObjectSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;

    /**
     * Opens the execution profile selection and returns the list of selected profiles after users press OK button.
     * 
     * @param parentShell parent shell of the dialog.
     * @param dialogTitle title of the dialog.
     * @return an array containing selected profiles.
     * @throws PlatformException thrown if KS could not initialize dialog and return the selected profiles
     * 
     * @since 1.0.13
     */
    ExecutionProfileEntity[] showExecutionProfileSelectionDialog(Shell parentShell, String dialogTitle) throws PlatformException;
    
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
