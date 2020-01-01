package ResourceEditor.EnemyEditor;

import GameControl.AEnemy;
import ResourceEditor.AResourceEditorTableModel;

public class AEnemyEditorTableModel extends AResourceEditorTableModel<AEnemy> {

    public AEnemyEditorTableModel() {
        super(new String[] {"ID", "Name"});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return rowIndex;
            case 1: return data.get(rowIndex).getName();
            default: return "err";
        }
    }

}
