package com.katalon.platform.ui.viewer;

import org.eclipse.jface.viewers.ITreeContentProvider;

public abstract class TypeCheckedTreeContentProvider<T> implements ITreeContentProvider {

    protected abstract Class<T> getElementType();

    private boolean isElementInstanceOf(Object element) {
        Class<?> clazz = getElementType();
        return clazz != null && clazz.isInstance(element);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] getChildren(Object parentElement) {
        if (isElementInstanceOf(parentElement)) {
            return getChildElements((T) parentElement);
        }
        return null;
    }

    protected abstract Object[] getChildElements(T parentElement);

    @SuppressWarnings("unchecked")
    @Override
    public Object getParent(Object element) {
        if (isElementInstanceOf(element)) {
            return getParentElement((T) element);
        }
        return null;
    }

    protected abstract Object getParentElement(T element);

    @SuppressWarnings("unchecked")
    @Override
    public boolean hasChildren(Object element) {
        return isElementInstanceOf(element) && hasChildElements((T) element);
    }

    protected abstract boolean hasChildElements(T element);

}
