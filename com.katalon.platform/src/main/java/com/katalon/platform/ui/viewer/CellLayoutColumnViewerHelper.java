package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;

public class CellLayoutColumnViewerHelper {
    private ColumnViewer viewer;

    public CellLayoutColumnViewerHelper(ColumnViewer viewer) {
        this.viewer = viewer;
    }

    public TypeCheckedStyleCellLabelProvider<?> getCellLabelProvider(int columnIndex) {
        if (viewer == null) {
            return null;
        }
        CellLabelProvider cellLabelProvider = viewer.getLabelProvider(columnIndex);
        if (cellLabelProvider instanceof TypeCheckedStyleCellLabelProvider<?>) {
            return (TypeCheckedStyleCellLabelProvider<?>) cellLabelProvider;
        }
        return null;
    }
}
