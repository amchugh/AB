package ResourceEditor;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AResourceEditorTable extends JPanel {

    public JTable table;
    public AResourceEditorTableModel tm;

    public AResourceEditorTable(AResourceEditorTableModel tm, AResourceEditorInputManager im) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.tm = tm;
        table = new JTable(tm);

        // First column will always be small
        TableColumn column = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMaxWidth(40); //first column is small
            }
            column.setResizable(true);
        }

        // Set the size
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        // Set how the table is selected
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);

        // We will only enable one selection at a time
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a listener to the table
        table.getSelectionModel().addListSelectionListener(im);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);

        // Add two buttons, one for removing and one for adding
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                im.onAddBP();
            }
        });
        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                im.onRemoveBP();
            }
        });
        bottom.add(add);
        bottom.add(remove);
        add(bottom);
    }

    public AResourceEditorTableModel getTableModel() {
        return tm;
    }

}
