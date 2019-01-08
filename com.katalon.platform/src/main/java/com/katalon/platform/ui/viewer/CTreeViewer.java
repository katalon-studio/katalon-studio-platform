package com.katalon.platform.ui.viewer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

public class CTreeViewer extends TreeViewer implements CustomColumnViewer {
    private static final int SECOND_CLICK = 2;

    private MouseListener mouseListener;

    public CTreeViewer(Composite parent, int style) {
        super(parent, style);
    }

    public CTreeViewer(Tree tree) {
        super(tree);
    }

    @Override
    public Widget getColumn(int columnIndex) {
        return getColumnViewerOwner(columnIndex);
    }

    @Override
    public ViewerRow getViewerRowFromWidgetItem(Widget item) {
        return getViewerRowFromItem(item);
    }

    @Override
    public ViewerCell getCell(Point point) {
        return super.getCell(point);
    }

    @Override
    public TypeCheckedStyleCellLabelProvider<?> getCellLabelProvider(int columnIndex) {
        return new CellLayoutColumnViewerHelper(this).getCellLabelProvider(columnIndex);
    }

    @Override
    public void enableTooltipSupport() {
        getTree().setToolTipText(StringUtils.EMPTY);
        ColumnViewerToolTipSupport.enableFor(this);
    }

    @Override
    protected void hookEditingSupport(Control control) {
        if (getColumnViewerEditor() == null) {
            return;
        }

        mouseListener = new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                // Workaround for bug 185817
                if (e.count != SECOND_CLICK) {
                    handleMouseDown(e);
                }
            }

            @Override
            public void mouseDoubleClick(MouseEvent e) {
                // Handle for the bug the mouse event for double click which have e.count = 1 and therefore creating a
                // ColumnViewerEditorActivationEvent object with type == MOUSE_CLICK_SELECTION
                // https://incubation.kms-technology.com/browse/KAT-1963
                if (e.count == SECOND_CLICK) {
                    handleMouseDown(e);
                }
            }
        };
        control.addMouseListener(mouseListener);
    }

    @Override
    protected void handleDispose(DisposeEvent event) {
        if (mouseListener != null && event.widget instanceof Control) {
            ((Control) event.widget).removeMouseListener(mouseListener);
            mouseListener = null;
        }
        super.handleDispose(event);
    }

    private void handleMouseDown(MouseEvent e) {
        ViewerCell cell = getCell(new Point(e.x, e.y));

        if (cell != null) {
            triggerEditorActivationEvent(new ColumnViewerEditorActivationEvent(cell, e));
        }
    }
}
