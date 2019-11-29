package GameControl;

import java.awt.*;

public class AEncounterStatusMenu extends AMenuManager {

    private AMenuFillableBar playerBar, enemyBar;

    public AEncounterStatusMenu(int playerMaxHP, int enemyMaxHP) {
        // Need 2 healthbars
        playerBar = new AMenuFillableBar(
                600, 20, 10, 100, playerMaxHP, Color.green, false
        );
        enemyBar = new AMenuFillableBar(
                600, 120, 10, 100, enemyMaxHP, Color.green, false
        );
        displayItems.add(playerBar);
        displayItems.add(enemyBar);
    }

    public void setBarValues(int playerHP, int enemyHP) {
        playerBar.setValue(playerHP);
        enemyBar.setValue(enemyHP);
    }

}
