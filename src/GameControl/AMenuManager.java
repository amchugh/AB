package GameControl;

import java.awt.*;
import java.util.ArrayList;

public class AMenuManager {

    protected ArrayList<AMenuSelectable> selectables;
    protected AMenuSelectable selected;

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
        selectables.forEach(s -> s.draw(g));
    }

    protected void setSelected(AMenuSelectable sel) {
        selected =sel;
        System.out.println("help");
    }

}
