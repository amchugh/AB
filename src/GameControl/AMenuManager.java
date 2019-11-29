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
        AMenuSelectable dir = switch (d) {
            case UP -> selected.getUp();
            case DOWN -> selected.getDown();
            case RIGHT -> selected.getRight();
            case LEFT -> selected.getLeft();
        };
        if (dir == null)
            return; //Not a valid direction
        setSelected(dir);
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
        throw new RuntimeException("This should never be called"); // todo:: uhhhhhh this a hugggeeee hack so like get rid of it
    }

}
