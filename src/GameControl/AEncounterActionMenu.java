package GameControl;

import java.awt.*;

public class AEncounterActionMenu extends AMenuManager {

    private static final Color disabledBackground = Color.LIGHT_GRAY;
    private static final Color defaultBackground = Color.red;
    private static final Color selectedBackground = Color.green;
    private static final Color textColor = Color.BLUE;

    /**
     * Takes in the names of all the actions. Will create boxes for all the actions,
     * and greyed boxes for all the unused actions (up to MAX_MOVES)
     * @param names
     */
    public AEncounterActionMenu(String[] names) {
        for (int i = 0; i < 4; i++) {
            Color bg;
            String text;
            if (i < names.length) {
                bg = defaultBackground;
                text = names[i];
            } else {
                bg = disabledBackground;
                text = "";
            }

            AMenuSelectableItem n = new AMenuSelectableItem(
                    text,
                    20 ,
                    20 + i * 100,
                    120 ,
                    100 + i*100,
                    bg, textColor, selectedBackground);

            selectables.add(n);
            displayItems.add(n);
        }
        // Set the connections
        for (int i = 0; i < names.length-1; i++) {
            selectables.get(i).setDown(selectables.get(i+1));
        }
        for (int i = names.length-1; i > 0; i--) {
            selectables.get(i).setUp(selectables.get(i-1));
        }
        setSelected(selectables.get(0));
    }

    @Override
    public void setSelected(AMenuSelectable sel) {
        if (selected != null) {
            // Reverse the color of the old one
            selected.onDeselected();
        }
        // Set the color of the new one
        sel.onSelected();
        // Set the new selected
        selected = sel;
    }

}
