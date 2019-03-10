package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.extension.TestCaseIntegrationViewDescription.PartActionService;
import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;
import com.katalon.platform.api.model.TestObjectEntity;

public interface TestObjectIntegrationViewDescription {
	/**
	 * Id of this extension point
	 * 
	 * @since 1.0.9
	 */
	String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testObjectIntegrationViewDescription";

	/**
	 * @param projectEntity
	 *            the working project
	 * @return true if this view is visible. Otherwise, false.
	 * @since 1.0.9
	 */
	default boolean isEnabled(ProjectEntity projectEntity) {
		return true;
	}

	/**
	 * @return Name of integration view
	 * @since 1.0.9
	 */
	String getName();

	/**
	 * @return clazz that implements TestObjectIntegrationView
	 * @since 1.0.9
	 */
	Class<? extends TestObjectIntegrationView> getTestObjectIntegrationView();

	/**
	 * Describes how the integration view is displayed.
	 * 
	 * @since 1.0.4
	 */
	public interface TestObjectIntegrationView {
		/**
		 * Creates the integration views.
		 * 
		 * @param parent
		 *            parent view of the integration view
		 * @param partActionService
		 *            utility service that helps to interact with Test Case
		 *            view.
		 * @param testObject
		 *            the working test object
		 * @return the created integration view
		 * @since 1.0.4
		 */
		Control onCreateView(Composite parent, PartActionService partActionService, TestObjectEntity testObject);

		/**
		 * @return Returns the current editing integration before KS performs
		 *         saving process. This method will be called if needsSaving
		 *         returns true.
		 * 
		 * @since 1.0.9
		 */
		default Integration getIntegrationBeforeSaving() {
			return null;
		}

		/**
		 * Indicates the current integration view that needs save the
		 * integration information. This method is called after users hit
		 * <b>Save</b>, or <b>Save All</b> button.
		 * <p>
		 * If this method returns true, Katalon Studio will invoke
		 * #getIntegrationBeforeSaving() to get new integration information.
		 * 
		 * @return true if the current editing integration need to be saved.
		 *         Otherwise, false.
		 * 
		 * @since 1.0.9
		 */
		default boolean needsSaving() {
			return false;
		}

		/**
		 * The success callback function after the current editing test case
		 * successfully saved.
		 * 
		 * @param updatedTestCase
		 *            the updated TestCaseEntity
		 * 
		 * @since 1.0.9
		 */
		default void onSaveSuccess(TestCaseEntity updatedTestCase) {
		}

		/**
		 * The failure callback function after the current editing test case was
		 * unable to save.
		 * 
		 * @param exception
		 *            the root cause failure
		 * 
		 * @since 1.0.9
		 */
		default void onSaveFailure(Exception exception) {
		}
	}
}
