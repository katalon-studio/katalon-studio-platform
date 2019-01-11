package com.katalon.platform.ui.viewer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;

public class CTableViewer extends TableViewer implements CustomColumnViewer {

    public CTableViewer(Composite parent, int style) {
        super(parent, style);
    }

    @Override
    public Widget getColumn(int columnIndex) {
        return getColumnViewerOwner(columnIndex);
    }

    @Override
    public ViewerRow getViewerRowFromWidgetItem(Widget item) {
        return super.getViewerRowFromItem(item);
    }
    
    @Override
    public ViewerRow getViewerRowFromItem(Widget item) {
        return super.getViewerRowFromItem(item);
    }

    @Override
    public ViewerCell getCell(Point point) {
        return super.getCell(point);
    }
    
    public void showLastItem() {
        Table table = getTable();
        if (table == null || table.isDisposed()) {
            return;
        }
        int lastItemIndex = table.getItemCount() - 1;
        if (lastItemIndex >= 0) {
            table.showItem(table.getItem(lastItemIndex));
        }
    }

    @Override
    public TypeCheckedStyleCellLabelProvider<?> getCellLabelProvider(int columnIndex) {
        return new CellLayoutColumnViewerHelper(this).getCellLabelProvider(columnIndex);
    }

    @Override
    public void enableTooltipSupport() {
        getTable().setToolTipText(StringUtils.EMPTY);
        ColumnViewerToolTipSupport.enableFor(this);
    }
}
