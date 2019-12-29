package ResourceEditor;

import GameControl.AResourceManager;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ABPTableSelectionHandler implements ListSelectionListener{
    
    AResourceEditorMain m;
    
    public ABPTableSelectionHandler(AResourceEditorMain m) {
        this.m=m;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            System.out.println("Selection changed!");
        } else {
            System.out.println("Mouseup");
            m.onSelectNewBP();
        }
    }
    
    public void onAddBP() {
        m.addBP();
    }
    
    public void onRemoveBP() {
        m.removeSelectedBP();
    }
    
}
