package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestSuiteEntity;

/**
 * TestCaseIntegrationViewDescription is the interface of
 * <code>com.katalon.platform.api.extension.testCaseIntegrationViewDescription</code>
 * extension point that allows client plugins can add a view in Test Suite integration tab.
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.testSuiteIntegrationViewDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * TestSuiteIntegrationViewDescription.
 * 
 * @since 1.0.9
 *
 */
public interface TestSuiteIntegrationViewDescription {
    /**
     * Id of this extension point
     * 
     * @since 1.0.9
     */
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testSuiteIntegrationViewDescription";

    /**
     * @param projectEntity the working project
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
     * @return clazz that implements TestSuiteIntegrationView
     * @since 1.0.9
     */
    Class<? extends TestSuiteIntegrationView> getTestSuiteIntegrationView();

    /**
     * Describes how the integration view is displayed.
     * 
     * @since 1.0.9
     */
    public interface TestSuiteIntegrationView {
        /**
         * Creates the integration views.
         * 
         * @param parent parent view of the integration view
         * @param partActionService utility service that helps to interact with Test Suite view.
         * @param testSuite the working test suite
         * @return the created integration view
         * @since 1.0.9
         */
        Control onCreateView(Composite parent, PartActionService partActionService, TestSuiteEntity testSuite);

        /**
         * @return Returns the current editing integration before KS performs saving process. This method will be called
         * if needsSaving returns true.
         * 
         * @since 1.0.9
         */
        default Integration getIntegrationBeforeSaving() {
            return null;
        }

        /**
         * Indicates the current integration view that needs save the integration information. This method is called
         * after users hit <b>Save</b>, or <b>Save All</b> button.
         * <p>
         * If this method returns true, Katalon Studio will invoke #getIntegrationBeforeSaving() to get new integration
         * information.
         * 
         * @return true if the current editing integration need to be saved. Otherwise, false.
         * 
         * @since 1.0.9
         */
        default boolean needsSaving() {
            return false;
        }

        /**
         * The success callback function after the current editing test suite successfully saved.
         * 
         * @param updatedTestSuite the updated TestSuiteEntity
         * 
         * @since 1.0.9
         */
        default void onSaveSuccess(TestSuiteEntity updatedTestSuite) {
        }

        /**
         * The failure callback function after the current editing test suite was unable to save.
         * 
         * @param exception the root cause failure
         * 
         * @since 1.0.9
         */
        default void onSaveFailure(Exception exception) {
        }
    }

    /**
     * A utility that helps the integration view can interact with Test Suite view.
     * 
     * @since 1.0.9
     */
    public interface PartActionService {
        /**
         * Marks the test suite view is able to save.
         * 
         * @since 1.0.9
         */
        void markDirty();

        /**
         * Checks the test suite view is able to save or not.
         * 
         * @return true if test suite view needs to save. Otherwise, false.
         * @since 1.0.9
         */
        boolean isDirty();
    }
}
