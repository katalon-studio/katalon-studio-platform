package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.widgets.Event;

public abstract class TypeCheckStyleCellTableLabelProvider <T> extends TypeCheckedStyleCellLabelProvider<T> {

    private CellLayoutInfo cellLayoutInfo;

    public TypeCheckStyleCellTableLabelProvider(int columnIndex) {
        super(columnIndex);
        cellLayoutInfo = new TableCellLayoutInfo();
    }

    @Override
    public void initialize(ColumnViewer viewer, ViewerColumn column) {
        super.initialize(viewer, column);
    }

    protected ViewerCell getOwnedViewerCell(Event event) {
        CTableViewer tableViewer = (CTableViewer) getViewer();
        return tableViewer.getViewerRowFromItem(event.item).getCell(columnIndex);
    }
    
    @Override
    protected boolean canNotDrawSafely(Object element) {
        return super.canNotDrawSafely(element) || !(getViewer() instanceof CTableViewer);
    }
    
    @Override
    public CellLayoutInfo getCellLayoutInfo() {
        return cellLayoutInfo;
    }
}
