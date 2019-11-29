package GameControl;

import java.awt.*;

public class AEncounterNarrationMenu extends AMenuManager {

    public AEncounterNarrationMenu(String text) {
        super();
        displayItems.add(
                new AMenuNarrationItem(text, 100, 100, 300, 200, Color.blue, Color.green)
        );
    }

}
