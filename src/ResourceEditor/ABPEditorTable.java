package ResourceEditor;

import GameControl.ABP;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ABPEditorTable extends JPanel {

    public BPTableModel tm;
    public JTable table;
    
    public ABPEditorTable(ABPTableSelectionHandler sh) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tm = new BPTableModel();
        table = new JTable(tm) {
        
        };
    
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMaxWidth(40); //first column is small
            } else {
                column.setPreferredWidth(50);
            }
            column.setResizable(true);
        }
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        // Set how the table is selected
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        
        // We will only enable one selection at a time
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add our context menu
        JPopupMenu menu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(table,
                    "Right-click performed on table and choose DELETE\n" +
                    tm.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });
        menu.add(delete);
        table.setComponentPopupMenu(menu);
    
        table.getSelectionModel().addListSelectionListener(sh);
        
        //Add the scroll pane to this panel.
        add(scrollPane);
        
        // Add two buttons, one for removing and one for adding
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sh.onAddBP();
            }
        });
        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sh.onRemoveBP();
            }
        });
        bottom.add(add);
        bottom.add(remove);
        Dimension d = remove.getPreferredSize();
        add(bottom);
    }
    
    public void updateTableData(ArrayList<ABP> n) {
        tm.updateTable(n);
    }
    
    private class BPTableModel extends AbstractTableModel {
        // todo:: load the proper data
        private String[] columnNames = {"ID", "Species Name", "Name"};
        private ArrayList<ABP> data = new ArrayList<>();
        
        public void updateTable(ArrayList<ABP> n) {
            data = n;
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount() {
            return data.size();
        }
    
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
    
        public String getColumnName(int col) {
            return columnNames[col];
        }
    
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: return rowIndex;
                case 1: return data.get(rowIndex).getSpecies().getName();
                case 2:
                    ABP bp = data.get(rowIndex);
                    if (bp.hasCustomName()) return data.get(rowIndex).getName();
                    else return "";
                default: return "Err";
            }
        }
    
        public Class getColumnClass(int c) {
            return String.class;
        }
    
        public boolean isCellEditable(int row, int col) {
            // None of our cells are editable in the table, so return false
            return false;
            /*
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (row < 1) {
                return false;
            } else {
                return true;
            }
             */
        }
        
    }
    
}
