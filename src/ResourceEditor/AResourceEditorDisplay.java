package ResourceEditor;

import GameControl.ABP;
import GameControl.ABPAction;
import GameControl.ABPSpecies;
import GameControl.AResourceManager;
import ResourceEditor.BPEditor.ABPEditorPanel;
import ResourceEditor.BPEditor.ABPEditorTableModel;
import ResourceEditor.EnemyEditor.AEnemyEditorPanel;
import ResourceEditor.EnemyEditor.AEnemyEditorTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AResourceEditorDisplay {
    
    public JPanel mainPanel;
    private JFrame frame;

    // BP Editor constructs
    private AResourceEditorTable bpTable;
    private ABPEditorPanel bpEditorPanel;

    // Enemy Editor constructs
    private AResourceEditorTable enemyTable;
    private AEnemyEditorPanel enemyEditorPanel;

    public enum EditorMode { BP, ENEMY };
    public EditorMode current;

    private AResourceEditorInputManager sh;
    
    public AResourceEditorDisplay(
            AResourceEditorInputManager sh,
            AResourceManager<ABPSpecies> sm,
            AResourceManager<ABPAction> am) {
        this.sh = sh;
        frame = new JFrame();
        frame.setTitle("Resource Editor");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800,600));

        // Create the menu bar
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

        JMenu m2 = new JMenu("Editor");
        JMenuItem e3 = new JMenuItem("BP");
        JMenuItem e4 = new JMenuItem("Enemies");

        m.add(e1);
        m.add(e2);
        mb.add(m);
        
        frame.setJMenuBar(mb);

        // Create the BP editor components
        ABPEditorTableModel bpTableModel = new ABPEditorTableModel();
        bpTable = new AResourceEditorTable(bpTableModel, sh);
        bpEditorPanel = new ABPEditorPanel(sm, am);

        // Create the Enemy editor components
        AEnemyEditorTableModel enemyTableModel = new AEnemyEditorTableModel();
        enemyTable = new AResourceEditorTable(enemyTableModel, sh);
        enemyEditorPanel = new AEnemyEditorPanel(sm, am);

        // Set the mode to BP editor by default
        setMode(EditorMode.BP);
    
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public void setMode(EditorMode m) {
        current = m;
        updateDisplay();
    }

    public void updateDisplay() {
        // Start by remove all the components
        mainPanel.removeAll();
        switch (current) {
            case BP:
                mainPanel.add(bpTable);
                mainPanel.add(bpEditorPanel);
                break;
            case ENEMY:
                mainPanel.add(enemyTable);
                mainPanel.add(enemyEditorPanel);
                break;
        }
    }
    
    public void selectNewBP(ABP bp, int sel) {
        bpEditorPanel.loadBP(bp, sel);
    }
    
    public ABP getEditedBP() {
        return bpEditorPanel.exportBP();
    }
    
    public int getSelectedRow() {
        return bpTable.table.getSelectedRow();
    }
    
    public void updateBPTableData(ArrayList<ABP> n) {
        bpTable.getTableModel().updateTable(n);
    }
    
}
