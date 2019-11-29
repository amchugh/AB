package GameControl;

import java.awt.*;

public interface AMenuSelectable {

    public AMenuSelectable getLeft();
    public AMenuSelectable getRight();
    public AMenuSelectable getUp();
    public AMenuSelectable getDown();

    public void setLeft(AMenuSelectable left);
    public void setRight(AMenuSelectable right);
    public void setUp(AMenuSelectable up);
    public void setDown(AMenuSelectable down);

    public void onSelected();
    public void onDeselected();

}
