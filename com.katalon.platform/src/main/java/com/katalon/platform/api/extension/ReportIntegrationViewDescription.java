package com.katalon.platform.api.extension;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.katalon.platform.api.exception.PlatformException;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.ReportEntity;
import com.katalon.platform.api.report.LogRecord;
import com.katalon.platform.api.report.TestCaseRecord;
import com.katalon.platform.api.report.TestStepRecord;
import com.katalon.platform.api.report.TestSuiteRecord;

/**
 * ReportIntegrationViewDescription is the interface of
 * <code>com.katalon.platform.api.extension.reportIntegrationViewDescription</code>
 * extension point that allows client plugins can:
 * <ul>
 * <li>add a single column on test case table of report view</li>
 * <li>add a single column on test logs table of each selected test case of report view</li>
 * <li>add a view in Integration tab of each selected test case of report view</li>
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
 *           extensionPointId="com.katalon.platform.api.extension.reportIntegrationViewDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * ReportIntegrationViewDescription.
 * 
 * @since 1.0.4
 *
 */
public interface ReportIntegrationViewDescription {
    /**
     * Id of this extension point
     * 
     * @since 1.0.4
     */
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.reportIntegrationViewDescription";

    /**
     * @return clazz that implements TestCaseColumnDescription
     * @since 1.0.4
     */
    Class<? extends TestCaseColumnDescription> getTestCaseColumnClass();

    /**
     * @return clazz that implements TestStepColumnDescription
     * @since 1.0.4
     */
    Class<? extends TestStepColumnDescription> getTestStepColumnClass();

    /**
     * @return clazz that implements TestCaseRecordIntegrationView
     * @since 1.0.4
     */
    Class<? extends TestCaseRecordIntegrationView> getTestCaseRecordViewClass();

    /**
     * @param projectEntity the working project
     * @return true if this view is visible. Otherwise, false.
     * @since 1.0.4
     */
    default boolean isEnabled(ProjectEntity projectEntity) {
        return true;
    }

    /**
     * Describes how the integration view in Integration tab of selected Test Case is displayed.
     * 
     * @since 1.0.4
     */
    public interface TestCaseRecordIntegrationView {
        /**
         * @return Tab name
         */
        String getName();

        /**
         * Creates the integration views
         * 
         * @param parent parent control
         * @param testSuiteRecord the log information of test suite
         * @param testCaseRecord the log information of the selected test case
         * @return the created integration view
         * @throws PlatformException thrown if client plugin could not create the view
         * 
         * @since 1.0.4
         */
        Control onCreateView(Composite parent, TestSuiteRecord testSuiteRecord, TestCaseRecord testCaseRecord)
                throws PlatformException;
    }

    /**
     * Describes how the integration column in Test Cases table is displayed.
     * 
     * @since 1.0.4
     */
    public interface TestCaseColumnDescription {
        /**
         * @return column name
         */
        String getName();

        /**
         * @param display current display
         * @return a 16x16 column image
         */
        default Image getColumnImage(Display display) {
            return null;
        }

        /**
         * @param reportEntity the working report
         * @param testSuiteRecord the log information of test suite
         * @return a CellDecorator that describes how every TestCaseRecord as a single line of Test Cases is decorated.
         * @since 1.0.4
         */
        CellDecorator<TestCaseRecord> onCreateLabelProvider(ReportEntity reportEntity, TestSuiteRecord testSuiteRecord);
    }

    /**
     * Describes how the integration column in Test Logs table is displayed.
     * 
     * @since 1.0.4
     */
    public interface TestStepColumnDescription {
        /**
         * @return column name
         */
        String getName();

        /**
         * @param display current display
         * @return a 16x16 column image
         */
        default Image getColumnImage(Display display) {
            return null;
        }

        /**
         * @param reportEntity the working report
         * @param testSuiteRecord the log information of test suite
         * @param testCaseRecord the log information of the selected test case
         * @return a CellDecorator that describes how every TestStepRecord as a single line of Test Cases is decorated.
         * @since 1.0.4
         */
        CellDecorator<TestStepRecord> onCreateLabelProvider(ReportEntity reportEntity, TestSuiteRecord testSuiteRecord,
                TestCaseRecord testCaseRecord);
    }

    /**
     * CellDecorator is a decorator service that help client plugins can describe, or express the integration
     * information of every single TestCase or TestLog in KS.
     *
     * @param <T> the type of LogRecord
     * @since 1.0.4
     */
    public interface CellDecorator<T extends LogRecord> {
        /**
         * 
         * @param record the record that needs to decorate
         * @return image of row of the given <code>record</code>
         * @since 1.0.4
         */
        default Image getImage(T record) {
            return null;
        }

        /**
         * 
         * @param record the record that needs to decorate
         * @return text at the row of the given <code>record</code>
         * @since 1.0.4
         */
        default String getText(T record) {
            return "";
        }

        /**
         * 
         * @param record the record that needs to decorate
         * @return tooltip at the row of the given <code>record</code>
         * @since 1.0.4
         */
        default String getToolTip(T record) {
            return "";
        }

        /**
         * 
         * @param record the record that needs to decorate
         * @return image of row of the given <code>record</code> when user is hovering on the record
         * @since 1.0.4
         */
        default Image getHoveredImage(T record) {
            return getImage(record);
        }

        /**
         * Handle mouse down event on the record
         * 
         * @param mouseEvent mouse event
         * @param record the selected record
         * @since 1.0.4
         */
        default void onMouseDownEvent(MouseEvent mouseEvent, T record) {

        }

        /**
         * 
         * @param record the selected record
         * @return true if KS will display a hand cursor when users are hovering on the record. Otherwise, false.
         * @since 1.0.4
         */
        default boolean showCursorOnHover(T record) {
            return false;
        }
    }
}
