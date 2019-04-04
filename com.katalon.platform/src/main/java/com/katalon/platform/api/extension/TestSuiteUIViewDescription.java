package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestSuiteEntity;

/**
 * TestSuiteUIViewDescription is the interface of
 * <code>com.katalon.platform.api.extension.testSuiteUIViewDescription</code>
 * extension point that allows client plugins can:
 * <ul>
 * <li>Add a custom composite to test suite view</li>
 * </ul>
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.testSuiteUIViewDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that
 * implements TestSuiteUIViewDescription.
 * 
 * @since 1.0.11
 *
 */
public interface TestSuiteUIViewDescription {
	/**
	 * Id of this extension point
	 * 
	 * @since 1.0.11
	 */
	String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testSuiteUIViewDescription";

	/**
	 * @param projectEntity
	 *            the working project
	 * @return true if this view is visible. Otherwise, false.
	 * @since 1.0.11
	 */
	default boolean isEnabled(ProjectEntity projectEntity) {
		return true;
	}

	/**
	 * @return Name of this view
	 * @since 1.0.11
	 */
	String getName();

	/**
	 * @return clazz that implements TestSuiteUIView
	 * @since 1.0.11
	 */
	Class<? extends TestSuiteUIView> getTestSuiteUIView();

	default boolean shouldBeRendered() {
		return true;
	}

	/**
	 * A utility that helps the integration view can interact with Test Suite
	 * view.
	 * 
	 * @since 1.0.11
	 */
	public interface PartActionService {
		/**
		 * Marks the test suite view is able to save.
		 * 
		 * @since 1.0.11
		 */
		void markDirty();

		/**
		 * Checks the test suite view is able to save or not.
		 * 
		 * @return true if test suite view needs to save. Otherwise, false.
		 * @since 1.0.11
		 */
		boolean isDirty();
	}

	/**
	 * Describes how the view is displayed.
	 * 
	 * @since 1.0.11
	 */
	public interface TestSuiteUIView {
		/**
		 * Creates the view
		 * 
		 * @param parent
		 *            parent view of this view
		 * @param partActionService
		 *            utility service that helps to interact with Test Suite
		 *            view.
		 * @param testSuite
		 *            the working test suite
		 * @return the created view
		 * @since 1.0.11
		 */
		Control onCreateView(Composite parent, PartActionService partActionService, TestSuiteEntity testSuiteEntity);

		/**
		 * The callback function after the Test Suite Collection view has been
		 * rendered. Data initialization should occur inside this implementation
		 * 
		 * @since 1.0.11
		 */
		void onPostCreateView();

		/**
		 * The success callback function after the current editing test suite
		 * successfully saved.
		 * 
		 * @param updatedTestSuite
		 *            the updated TestSuiteEntity
		 * 
		 * @since 1.0.11
		 */
		default void onSaveSuccess(TestSuiteEntity updatedTestSuite) {

		}

		/**
		 * The failure callback function after the current editing test suite
		 * was unable to save.
		 * 
		 * @param exception
		 *            the root cause failure
		 * 
		 * @since 1.0.11
		 */
		default void onSaveFailure(Exception exception) {

		}
	}
}