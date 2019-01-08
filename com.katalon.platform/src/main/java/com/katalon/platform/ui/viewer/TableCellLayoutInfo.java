package com.katalon.platform.ui.viewer;

public class TableCellLayoutInfo implements CellLayoutInfo {

    private static final int DF_TABLE_CELL_MARGIN = 5;

    @Override
    public int getLeftMargin() {
        return DF_TABLE_CELL_MARGIN;
    }

    @Override
    public int getRightMargin() {
        return DF_TABLE_CELL_MARGIN;
    }

    @Override
    public int getSpace() {
        return DF_TABLE_CELL_MARGIN;
    }

}
