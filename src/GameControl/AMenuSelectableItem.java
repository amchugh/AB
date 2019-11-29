package GameControl;

import java.awt.*;

public class AMenuSelectableItem extends AMenuNarrationItem implements AMenuSelectable {

    private AMenuSelectable left;
    private AMenuSelectable right;
    private AMenuSelectable up;
    private AMenuSelectable down;

    private Color selectColor;
    private Color backgroundColor;

    public AMenuSelectableItem(String text, int x1, int y1, int x2, int y2, Color backgroundColor, Color textColor, Color selectColor) {
        super(text, x1, y1, x2, y2, backgroundColor, textColor);
        this.backgroundColor = backgroundColor;
        this.selectColor = selectColor;
    }

    public AMenuSelectable getLeft() {
        return left;
    }

    public void setLeft(AMenuSelectable left) {
        this.left = left;
    }

    public AMenuSelectable getRight() {
        return right;
    }

    public void setRight(AMenuSelectable right) {
        this.right = right;
    }

    public AMenuSelectable getUp() {
        return up;
    }

    public void setUp(AMenuSelectable up) {
        this.up = up;
    }

    public AMenuSelectable getDown() {
        return down;
    }

    public void setDown(AMenuSelectable down) {
        this.down = down;
    }

    public void onSelected() {
        setBackgroundColor(selectColor);
    }

    public void onDeselected() {
        setBackgroundColor(backgroundColor);
    }

}
