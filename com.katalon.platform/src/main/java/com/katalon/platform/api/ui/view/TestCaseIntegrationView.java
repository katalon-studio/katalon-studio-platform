package com.katalon.platform.api.ui.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.TestCaseEntity;

public interface TestCaseIntegrationView {
    Control onCreateView(Composite parent, PartActionService partActionService, TestCaseEntity testCase);

    void onDestroyView();
}