package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestCaseEntity;

public interface TestCaseIntegrationViewDescription {
    String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testCaseIntegrationViewDescription";

    default boolean isEnabled(ProjectEntity projectEntity) {
        return true;
    }
    
    String getName();

    Class<? extends TestCaseIntegrationView> getTestCaseIntegrationView();

    public interface TestCaseIntegrationView {
        Control onCreateView(Composite parent, PartActionService partActionService, TestCaseEntity testCase);
    }

    public interface PartActionService {
        void markDirty();

        boolean isDirty();
    }
}
