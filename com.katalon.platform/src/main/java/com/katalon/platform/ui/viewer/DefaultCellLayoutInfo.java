package com.katalon.platform.ui.viewer;

import org.eclipse.core.runtime.Platform;

public class DefaultCellLayoutInfo implements CellLayoutInfo {

    @Override
    public int getLeftMargin() {
        return Platform.OS_WIN32.equals(Platform.getOS()) ? 0 : 2;
    }

    @Override
    public int getRightMargin() {
        return 0;
    }

    @Override
    public int getSpace() {
        return 5;
    }
}
