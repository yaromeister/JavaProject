package com.arsen.desktop;

import javax.swing.table.AbstractTableModel;

public class AllTableModel extends AbstractTableModel {
    private Object[][] data;
    private String[] columnNames;

    public AllTableModel(){
        data = DataBaseManager.getColumnDataDB();
        columnNames = DataBaseManager.getColumnNamesDB();
    }

    public void refreshTableData(Object[][] data){
        this.data = data;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        this.data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex,columnIndex);
    }
    public String getColumnName(int col) {
        return columnNames[col];
    }
}
