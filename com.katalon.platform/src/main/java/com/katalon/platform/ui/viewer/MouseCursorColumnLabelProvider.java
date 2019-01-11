package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public abstract class MouseCursorColumnLabelProvider <T> extends TypeCheckedStyleCellLabelProvider<T> {

    public MouseCursorColumnLabelProvider(int columnIndex) {
        super(columnIndex);
    }

    @Override
    public void initialize(ColumnViewer viewer, ViewerColumn column) {
        super.initialize(viewer, column);
        registerMouseListeners(viewer);
    }

    private void registerMouseListeners(ColumnViewer viewer) {
        Control table = getControl(viewer);

        final CellClickedListener mouseListener = new CellClickedListener();
        table.addMouseListener(mouseListener);

        final MoveMouseOnCellListener mouseMoveListener = new MoveMouseOnCellListener();
        table.addMouseMoveListener(mouseMoveListener);

        table.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                Control control = (Control) e.getSource();
                control.removeMouseListener(mouseListener);
                control.removeMouseMoveListener(mouseMoveListener);
            }
        });
    }

    private final class CellClickedListener extends MouseAdapter {

        private static final int LEFT_CLICK = 1;

        @Override
        public void mouseUp(MouseEvent e) {
            if (e.button != LEFT_CLICK) {
                return;
            }
            Point currentMouseLocation = new Point(e.x, e.y);
            ViewerCell cell = getViewer().getCell(currentMouseLocation);
            if (isPlacedMouseHover(cell) && shouldShowCursor(cell, currentMouseLocation)) {
                handleMouseDown(e, cell);
            }
        }
    }
    
    protected boolean shouldShowCursor(ViewerCell cell, Point currentMouseLocation) {
        return cell.getBounds().contains(currentMouseLocation);
    }

    protected abstract void handleMouseDown(MouseEvent e, ViewerCell cell);

    private final class MoveMouseOnCellListener implements MouseMoveListener {
        @Override
        public void mouseMove(MouseEvent e) {
            ColumnViewer viewer = getViewer();
            Point currentMouseLocation = new Point(e.x, e.y);
            ViewerCell cell = viewer.getCell(currentMouseLocation);
            Control table = getControl(viewer);
            if (cell == null) {
                if (table.getCursor() != null) {
                    table.getCursor().dispose();
                }
                table.setCursor(null);
                return;
            }
            if (!isPlacedMouseHover(cell) || !shouldShowCursor(cell, currentMouseLocation)) {
                int cellIndex = cell.getColumnIndex();
                if (!(getViewer().getLabelProvider(cellIndex) instanceof MouseCursorColumnLabelProvider)
                        || cellIndex == columnIndex) {
                    if (table.getCursor() != null) {
                        table.getCursor().dispose();
                    }
                    table.setCursor(null);
                }
                return;
            }

            if (table.getCursor() == null) {
                table.setCursor(newCursor());
            }
        }

        private Cursor newCursor() {
            return new Cursor(getControl(getViewer()).getDisplay(), SWT.CURSOR_HAND);
        }
    }

    protected Control getControl(ColumnViewer viewer) {
        return viewer.getControl();
    }

    protected boolean isPlacedMouseHover(ViewerCell cell) {
        return cell != null && cell.getColumnIndex() == columnIndex;
    }
}
