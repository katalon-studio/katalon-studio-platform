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

public interface ReportIntegrationViewDescription {
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.reportIntegrationViewDescription";

    Class<? extends TestCaseColumnDescription> getTestCaseColumnClass();

    Class<? extends TestStepColumnDescription> getTestStepColumnClass();

    Class<? extends TestCaseRecordIntegrationView> getTestCaseRecordViewClass();

    default boolean isEnabled(ProjectEntity projectEntity) {
        return true;
    }

    public interface TestCaseRecordIntegrationView {
        String getName();

        Control onCreateView(Composite parent, TestSuiteRecord testSuiteRecord, TestCaseRecord testCaseRecord)
                throws PlatformException;
    }

    public interface TestCaseColumnDescription {
        String getName();

        default Image getColumnImage(Display display) {
            return null;
        }

        CellDecorator<TestCaseRecord> onCreateLabelProvider(ReportEntity reportEntity, TestSuiteRecord testSuiteRecord);
    }

    public interface TestStepColumnDescription {
        String getName();

        default Image getColumnImage(Display display) {
            return null;
        }

        CellDecorator<TestStepRecord> onCreateLabelProvider(ReportEntity reportEntity, TestSuiteRecord testSuiteRecord,
                TestCaseRecord testCaseRecord);
    }

    public interface CellDecorator<T extends LogRecord> {
        default Image getImage(T record) {
            return null;
        }

        default String getText(T record) {
            return "";
        }

        default String getToolTip(T record) {
            return "";
        }

        default Image getHoveredImage(T record) {
            return getImage(record);
        }

        default void onMouseDownEvent(MouseEvent mouseEvent, T record) {

        }

        default boolean showCursorOnHover(T record) {
            return false;
        }
    }
}
