package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;

public abstract class TypeCheckedStyleTreeCellLabelProvider<T> extends TypeCheckedStyleCellLabelProvider<T> {

    public TypeCheckedStyleTreeCellLabelProvider(int columnIndex) {
        super(columnIndex);
    }

    @Override
    public void initialize(ColumnViewer viewer, ViewerColumn column) {
        super.initialize(viewer, column);
    }

    protected ViewerCell getOwnedViewerCell(Event event) {
        CTreeViewer treeViewer = (CTreeViewer) getViewer();
        return treeViewer.getViewerRowFromWidgetItem(event.item).getCell(columnIndex);
    }

    @Override
    protected boolean canNotDrawSafely(Object element) {
        return super.canNotDrawSafely(element) || !(getViewer() instanceof CTreeViewer);
    }

    @Override
    protected Rectangle getTextBounds(Rectangle originalBounds) {
        return new Rectangle(originalBounds.x, originalBounds.y, originalBounds.width + 1, originalBounds.height);
    }
}
