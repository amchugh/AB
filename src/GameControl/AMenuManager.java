package GameControl;

import java.awt.*;
import java.util.ArrayList;

public class AMenuManager {

    protected ArrayList<AMenuItem> displayItems;
    protected ArrayList<AMenuSelectable> selectables;
    protected AMenuSelectable selected;

    public AMenuManager() {
        selectables = new ArrayList<>();
        displayItems = new ArrayList<>();
    }

    public void attemptMove(AMenuFlowProvider.FlowDirection d) {
        AMenuSelectable newSel = switch (d) {
            case UP -> selected.getUp();
            case DOWN -> selected.getDown();
            case RIGHT -> selected.getRight();
            case LEFT -> selected.getLeft();
        };
        if (newSel == null) {
            // there is no item in that direction
            return;
        }
        setSelected(newSel);
    }

    public void draw(Graphics g) {
        displayItems.forEach(s -> s.draw(g));
    }

    public int getSelectedIndex() {
        for (int i = 0; i < selectables.size(); i++) {
            if (selectables.get(i) == selected) {
                return i;
            }
        }
        throw new RuntimeException("Selected item is not in Array of selectable items");
    }

    public void setSelected(AMenuSelectable sel) {
        if (selectables.contains(sel)) selected = sel;
        else throw new IllegalArgumentException("Attempted to 'select' a menu object that does not exist in this menu");
    }

}
