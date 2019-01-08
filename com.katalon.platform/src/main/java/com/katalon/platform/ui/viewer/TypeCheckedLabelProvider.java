package com.katalon.platform.ui.viewer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public abstract class TypeCheckedLabelProvider<T> extends LabelProvider implements ITableLabelProvider {

    protected abstract Class<T> getElementType();

    private boolean isElementInstanceOf(Object element) {
        Class<?> clazz = getElementType();
        return clazz != null && clazz.isInstance(element);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        if (isElementInstanceOf(element)) {
            return getColumnImageByIndex((T) element, columnIndex);
        }
        return null;
    }

    protected abstract Image getColumnImageByIndex(T element, int columnIndex);

    @SuppressWarnings("unchecked")
    @Override
    public String getColumnText(Object element, int columnIndex) {
        if (isElementInstanceOf(element)) {
            return getColumnTextByIndex((T) element, columnIndex);
        }
        return StringUtils.EMPTY;
    }

    protected abstract String getColumnTextByIndex(T element, int columnIndex);

}
