package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.Integration;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;

/**
 * TestCaseIntegrationViewDescription is the interface of
 * <code>com.katalon.platform.api.extension.testCaseIntegrationViewDescription</code>
 * extension point that allows client plugins can add a view in Test Case integration tab.
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.testCaseIntegrationViewDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * TestCaseIntegrationViewDescription.
 * 
 * @since 1.0.4
 *
 */
public interface TestCaseIntegrationViewDescription {
    /**
     * Id of this extension point
     * 
     * @since 1.0.4
     */
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testCaseIntegrationViewDescription";

    /**
     * @param projectEntity the working project
     * @return true if this view is visible. Otherwise, false.
     * @since 1.0.4
     */
    default boolean isEnabled(ProjectEntity projectEntity) {
        return true;
    }

    /**
     * @return Name of integration view
     * @since 1.0.4
     */
    String getName();

    /**
     * @return clazz that implements TestCaseIntegrationView
     * @since 1.0.4
     */
    Class<? extends TestCaseIntegrationView> getTestCaseIntegrationView();

    /**
     * Describes how the integration view is displayed.
     * 
     * @since 1.0.4
     */
    public interface TestCaseIntegrationView {
        /**
         * Creates the integration views.
         * 
         * @param parent parent view of the integration view
         * @param partActionService utility service that helps to interact with Test Case view.
         * @param testCase the working test case
         * @return the created integration view
         * @since 1.0.4
         */
        Control onCreateView(Composite parent, PartActionService partActionService, TestCaseEntity testCase);

        /**
         * @return Returns the current editing integration before KS performs saving process. This method will be called
         * if needsSaving returns true.
         * 
         * @since 1.0.7
         */
        default Integration getIntegrationBeforeSaving() {
            return null;
        }

        /**
         * Indicates the current integration view that needs save the integration information. This method is called
         * after users hit <b>Save</b>, or <b>Save All</b> button.
         * <p>
         * If this method returns true, Katalon Studio will invoke #getIntegrationBeforeSaving() to get new integration information.
         * 
         * @return true if the current editing integration need to be saved. Otherwise, false.
         * 
         * @since 1.0.7
         */
        default boolean needsSaving() {
            return false;
        }
    }

    /**
     * A utility that helps the integration view can interact with Test Case view.
     * 
     * @since 1.0.4
     */
    public interface PartActionService {
        /**
         * Marks the test case view is able to save.
         * 
         * @since 1.0.4
         */
        void markDirty();

        /**
         * Checks the test case view is able to save or not.
         * 
         * @return true if test case view needs to save. Otherwise, false.
         * @since 1.0.4
         */
        boolean isDirty();
    }
}
