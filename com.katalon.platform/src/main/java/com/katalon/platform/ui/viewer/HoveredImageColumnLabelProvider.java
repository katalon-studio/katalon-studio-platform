package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

public abstract class HoveredImageColumnLabelProvider<T> extends MouseCursorColumnLabelProvider<T> {

    public HoveredImageColumnLabelProvider(int columnIndex) {
        super(columnIndex);
    }

    @Override
    public void initialize(ColumnViewer viewer, ViewerColumn column) {
        super.initialize(viewer, column);
        registerMouseListener(viewer);
    }

    private void registerMouseListener(ColumnViewer viewer) {
        Control table = getControl(viewer);

        final ImageHoveredListener mouseMoveListener = new ImageHoveredListener();
        table.addMouseMoveListener(mouseMoveListener);
        table.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                Control control = (Control) e.getSource();
                control.removeMouseMoveListener(mouseMoveListener);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawCellTextAndImage(Event event, ViewerCell cell, GC gc) {
        boolean shouldShowCursor = shouldShowCursor(cell,
                cell.getControl().toControl(event.display.getCursorLocation()));
        cell.setImage(shouldShowCursor ? getHoveredImage((T) cell.getElement()) : getImage((T) cell.getElement()));
        super.drawCellTextAndImage(event, cell, gc);
    }

    protected abstract Image getHoveredImage(T element);

    private final class ImageHoveredListener implements MouseMoveListener {
        private ViewerCell lastFocusedIn;

        @Override
        public void mouseMove(MouseEvent e) {
            ColumnViewer viewer = getViewer();
            Point currentMouseLocation = new Point(e.x, e.y);
            ViewerCell cell = viewer.getCell(currentMouseLocation);
            try {
                if (!isPlacedMouseHover(cell) || !shouldShowCursor(cell, currentMouseLocation)) {
                    cell = null;
                    return;
                }
            } finally {
                if (lastFocusedIn == cell) {
                    return;
                }
                if (lastFocusedIn != null) {
                    getViewer().refresh(lastFocusedIn.getElement());
                }
                if (cell != null) {
                    getViewer().refresh(cell.getElement());
                }
                lastFocusedIn = cell;
            }
        }
    }
}
