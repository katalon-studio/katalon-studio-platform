package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public abstract class ImageCenterLabelProvider<T> extends TypeCheckedStyleCellLabelProvider<T> {

    public ImageCenterLabelProvider(int columnIndex) {
        super(columnIndex);
    }

    @Override
    protected boolean canNotDrawSafely(Object element) {
        return super.canNotDrawSafely(element) || !(getViewer() instanceof CustomColumnViewer);
    }

    protected void drawCellTextAndImage(Event event, ViewerCell cell, GC gc) {
        Image image = cell.getImage();

        Rectangle textBounds = getTextBounds(cell.getTextBounds());

        TextLayout textLayout = getSharedTextLayout(event.display);

        int textWidth = textLayout.getBounds().width;

        int startX = event.getBounds().x + getLeftMargin();
        if (image != null) {
            int textAndImageWidth = cell.getBounds().width - getLeftMargin() - getRightMargin()
                    - image.getBounds().width;
            if (textWidth > 0) {
                textAndImageWidth -= textWidth + getSpace();
                startX += getSpace();
            }
            if (textAndImageWidth > 0) {
                startX += textAndImageWidth / 2;
            }
            gc.drawImage(image, startX, event.getBounds().y);
        }

        if (textWidth > 0) {
            Rectangle layoutBounds = textLayout.getBounds();
            int y = textBounds.y + Math.max(0, (textBounds.height - layoutBounds.height) / 2);

            Rectangle saveClipping = gc.getClipping();
            gc.setClipping(textBounds);

            int style = getColumn(cell.getColumnIndex()).getStyle();
            int x = textBounds.x;
            if ((style & SWT.RIGHT) != 0) {
                x = textBounds.x + textBounds.width - textWidth;
            } else if ((style & SWT.CENTER) != 0) {
                x = textBounds.x + (textBounds.width - textWidth) / 2;
            }
            textLayout.draw(gc, x, y);
            gc.setClipping(saveClipping);
        }
    }

    private Widget getColumn(int columnIndex) {
        CustomColumnViewer columnViewer = (CustomColumnViewer) getViewer();
        return columnViewer.getColumn(columnIndex);
    }
}
