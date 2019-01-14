package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public abstract class TypeCheckedStyleCellLabelProvider<T> extends StyledCellLabelProvider {

    private static final int DF_MARGIN = 0;

    protected int columnIndex;

    private TextLayout cachedTextLayout;

    private boolean customPaint;

    private int deltaOfLastMeasure;

    /**
     * Create new instance of {@link TypeCheckedStyleCellLabelProvider} with default <code>customPaint = true</code>
     * @param columnIndex the index of column in viewer
     */
    public TypeCheckedStyleCellLabelProvider(final int columnIndex) {
        this(columnIndex, true);
    }

    /**
     *
     * @param columnIndex the index of column in viewer
     * @param customPaint true if children want to use {@link #paint(Event, Object)}. Otherwise, uses
     * {@link StyledCellLabelProvider#paint(Event, Object)}
     */
    public TypeCheckedStyleCellLabelProvider(final int columnIndex, final boolean customPaint) {
        this.columnIndex = columnIndex;
        this.customPaint = customPaint;
    }

    protected abstract Class<T> getElementType();

    private boolean isElementInstanceOf(Object element) {
        Class<?> clazz = getElementType();
        return clazz != null && clazz.isInstance(element);
    }

    @Override
    protected void paint(Event event, Object element) {
        if (canNotDrawSafely(element)) {
            super.paint(event, element);
            return;
        }

        ViewerCell cell = getOwnedViewerCell(event);

        if (isCellNotExisted(cell)) {
            return;
        }

        GC gc = event.gc;
        boolean applyColors = useColors(event);

        Color oldForeground = gc.getForeground();
        Color oldBackground = gc.getBackground();

        if (applyColors) {
            drawCellColor(cell, gc);
        }

        drawCellTextAndImage(event, cell, gc);

        if (canDrawFocus(event)) {
            drawCellFocus(cell, gc);
        }

        if (applyColors) {
            gc.setForeground(oldForeground);
            gc.setBackground(oldBackground);
        }
    }

    public CellLayoutInfo getCellLayoutInfo() {
        return new DefaultCellLayoutInfo();
    }

    protected final int getSpace() {
        CellLayoutInfo layoutInfo = getCellLayoutInfo();
        return layoutInfo != null ? layoutInfo.getSpace() : DF_MARGIN;
    }

    protected final int getLeftMargin() {
        CellLayoutInfo layoutInfo = getCellLayoutInfo();
        return layoutInfo != null ? layoutInfo.getLeftMargin() : DF_MARGIN;
    }

    protected final int getRightMargin() {
        CellLayoutInfo layoutInfo = getCellLayoutInfo();
        return layoutInfo != null ? layoutInfo.getRightMargin() : DF_MARGIN;
    }

    protected boolean canNotDrawSafely(Object element) {
        return !customPaint || !isElementInstanceOf(element) || !(getViewer() instanceof CustomColumnViewer);
    }

    private void drawCellColor(ViewerCell cell, GC gc) {
        Color foreground = cell.getForeground();
        if (foreground != null) {
            gc.setForeground(foreground);
        }

        Color background = cell.getBackground();
        if (background != null) {
            gc.setBackground(background);
        }
    }

    private void drawCellFocus(ViewerCell cell, GC gc) {
        Rectangle focusBounds = getTextBounds(cell.getViewerRow().getBounds());
        gc.drawFocus(focusBounds.x, focusBounds.y, focusBounds.width + deltaOfLastMeasure + getRightMargin(),
                focusBounds.height);
    }

    private boolean canDrawFocus(Event event) {
        return (event.detail & SWT.FOCUSED) != 0;
    }

    protected void drawCellTextAndImage(Event event, ViewerCell cell, GC gc) {
        int startX = drawImage(event, cell, gc, cell.getImage());

        Rectangle textBounds = getTextBounds(cell.getTextBounds());
        if (textBounds != null) {
            TextLayout textLayout = getSharedTextLayout(event.display);

            Rectangle layoutBounds = textLayout.getBounds();
            int y = textBounds.y + Math.max(0, (textBounds.height - layoutBounds.height) / 2);

            // Rectangle saveClipping = gc.getClipping();
            // gc.setClipping(textBounds);
            textLayout.draw(gc, startX, y);

            // gc.setClipping(saveClipping);
        }
    }

    protected int drawImage(Event event, ViewerCell cell, GC gc, Image image) {
        Rectangle eventBounds = cell.getImageBounds();
        int startX = getLeftMargin();
        if (image != null) {
            int y = eventBounds.y + Math.max(0, (eventBounds.height - image.getBounds().height) / 2);
            gc.drawImage(image, eventBounds.x + startX, y);
            startX += getSpace() + image.getBounds().width;
        }
        return startX + eventBounds.x;
    }

    protected Rectangle getTextBounds(Rectangle originalBounds) {
        return originalBounds;
    }

    protected ViewerCell getOwnedViewerCell(Event event) {
        ColumnViewer columnViewer = getViewer();
        if (columnViewer instanceof CustomColumnViewer) {
            return ((CustomColumnViewer) columnViewer).getViewerRowFromWidgetItem(event.item).getCell(columnIndex);
        }
        return columnViewer.getCell(new Point(event.x, event.y));
    }

    @Override
    protected void measure(Event event, Object element) {
        if (canNotDrawSafely(element)) {
            super.measure(event, element);
            return;
        }

        ViewerCell cell = getOwnedViewerCell(event);

        if (isCellNotExisted(cell)) {
            return;
        }

        boolean applyColors = useColors(event);

        TextLayout layout = getSharedTextLayout(event.display);

        int textWidthDelta = deltaOfLastMeasure = updateTextLayout(layout, cell, applyColors)
                + updateImageLayout(event, cell);

        event.width += textWidthDelta + getRightMargin() + getSpace() + getLeftMargin();
    }

    protected int updateImageLayout(Event layout, ViewerCell cell) {
        return 0;
    }

    protected boolean isCellNotExisted(ViewerCell cell) {
        return cell == null || cell.getViewerRow() == null;
    }

    protected boolean useColors(Event event) {
        return (event.detail & SWT.SELECTED) == 0;
    }

    private int updateTextLayout(TextLayout layout, ViewerCell cell, boolean applyColors) {
        layout.setStyle(null, 0, Integer.MAX_VALUE); // clear old styles

        layout.setText(cell.getText());
        layout.setFont(cell.getFont()); // set also if null to clear previous usages

        int originalTextWidth = getTextBounds(layout.getBounds()).width; // text width without any styles
        boolean containsOtherFont = false;

        StyleRange[] styleRanges = cell.getStyleRanges();
        if (styleRanges != null) { // user didn't fill styled ranges
            for (int i = 0; i < styleRanges.length; i++) {
                StyleRange curr = prepareStyleRange(styleRanges[i], applyColors);
                layout.setStyle(curr, curr.start, curr.start + curr.length - 1);
                if (curr.font != null) {
                    containsOtherFont = true;
                }
            }
        }
        int textWidthDelta = 0;
        if (containsOtherFont) {
            textWidthDelta = getTextBounds(layout.getBounds()).width - originalTextWidth;
        }
        return textWidthDelta;
    }

    protected TextLayout getSharedTextLayout(Display display) {
        if (cachedTextLayout == null) {
            int orientation = getViewer().getControl().getStyle() & (SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT);
            cachedTextLayout = new TextLayout(display);
            cachedTextLayout.setOrientation(orientation);
        }
        return cachedTextLayout;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(ViewerCell cell) {
        if (isElementInstanceOf(cell.getElement())) {
            T element = (T) cell.getElement();
            cell.setText(getText(element));
            cell.setImage(getImage(element));
            cell.setBackground(getBackground(cell.getBackground(), element));
            cell.setForeground(getForeground(cell.getForeground(), element));
            cell.setStyleRanges(getStyleRanges(cell, element));
        }

        super.update(cell);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getToolTipText(Object element) {
        if (isElementInstanceOf(element)) {
            return getElementToolTipText((T) element);
        }
        return super.getToolTipText(element);
    }

    protected String getElementToolTipText(T element) {
        return null;
    }

    protected Color getBackground(Color background, T element) {
        return null;
    }

    protected Color getForeground(Color foreground, T element) {
        return null;
    }

    protected abstract Image getImage(T element);

    protected abstract String getText(T element);

    protected StyleRange[] getStyleRanges(ViewerCell cell, T element) {
        return null;
    }
}
