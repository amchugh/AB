package ResourceEditor;

import GameControl.ABP;
import GameControl.ABPSpecies;
import GameControl.AResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AResourceEditorDisplay {
    
    public JPanel mainPanel;
    private JFrame frame;
    
    private ABPEditorTable table;
    private ABPEditorPanel editorPanel;
    
    private ABPTableSelectionHandler sh;
    
    public AResourceEditorDisplay(ABPTableSelectionHandler sh, AResourceManager<ABPSpecies> sm) {
        this.sh = sh;
        frame = new JFrame();
        frame.setTitle("Resource Editor");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800,600));
        
        table = new ABPEditorTable(sh);
        editorPanel = new ABPEditorPanel(sm);
    
        // Create a menu bar
        JMenuBar mb = new JMenuBar();
    
        JMenu m = new JMenu("File");
        JMenuItem e1 = new JMenuItem("Export");
        JMenuItem e2 = new JMenuItem("Exit");
        e1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sh.onExport();
            }
        });
        e2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sh.onExit();
            }
        });
        
        m.add(e1);
        m.add(e2);
        mb.add(m);
        
        frame.setJMenuBar(mb);
        mainPanel.add(table);
        mainPanel.add(editorPanel);
    
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
    
    public void selectNewBP(ABP bp) {
        editorPanel.loadBP(bp);
    }
    
    public ABP getEditedBP() {
        return editorPanel.exportBP();
    }
    
    public int getSelectedRow() {
        return table.table.getSelectedRow();
    }
    
    public void updateTableData(ArrayList<ABP> n) {
        table.updateTableData(n);
    }
    
}
