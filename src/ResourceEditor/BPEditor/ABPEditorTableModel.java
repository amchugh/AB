package ResourceEditor.BPEditor;

import GameControl.ABP;
import ResourceEditor.AResourceEditorTableModel;

public class ABPEditorTableModel extends AResourceEditorTableModel<ABP> {

    public ABPEditorTableModel() {
        super(new String[] {"ID", "Species Name", "Custom Name"});
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

}
