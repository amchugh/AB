package GameControl;

import java.awt.*;
import java.util.ArrayList;

public class AEncounterMenuManager extends AMenuManager {

    public static final Color background = Color.red;
    public static final Color selectedBackground = Color.green;

    public AEncounterMenuManager() {
        selectables = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            selectables.add(
                    new AMenuSelectable(
                            "Test_" + i,
                            20 ,
                            20 + i * 100,
                            120 ,
                            100 + i*100,
                            background, Color.BLUE)
            );
        }
        // Set the connections
        for (int i = 0; i < 3; i++) {
            selectables.get(i).setDown(selectables.get(i+1));
        }
        for (int i = 3; i > 0; i--) {
            selectables.get(i).setUp(selectables.get(i-1));
        }
        setSelected(selectables.get(0));
    }

    @Override
    protected void setSelected(AMenuSelectable sel) {
        if (selected != null) {
            // Reverse the color of the old one
            selected.setBackgroundColor(background);
        }
        // Set the color of the new one
        sel.setBackgroundColor(selectedBackground);
        // Set the new selected
        selected = sel;
    }

}
