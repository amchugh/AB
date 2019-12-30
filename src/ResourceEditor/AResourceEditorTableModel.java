package ResourceEditor;

import GameControl.ABP;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public abstract class AResourceEditorTableModel<T> extends AbstractTableModel {

    // todo:: make the table update when the data is first loaded

    private String[] columnNames;
    protected ArrayList<T> data;

    public AResourceEditorTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        data = new ArrayList<T>();
    }

    public void updateTable(ArrayList<T> n) {
        data = n;
        fireTableDataChanged();
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    public Class getColumnClass(int c) { return String.class; }
    @Override
    public int getRowCount() { return data.size(); }
    @Override
    public int getColumnCount() { return columnNames.length; }
    @Override
    public boolean isCellEditable(int x, int y) { return false; }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

}
