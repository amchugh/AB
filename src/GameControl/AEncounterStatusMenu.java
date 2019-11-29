package GameControl;

import java.awt.*;

public class AEncounterStatusMenu extends AMenuManager {

    private static final Font textFont = new Font("Serif", Font.PLAIN, 24);

    private AMenuFillableBar playerBar, enemyBar;

    public AEncounterStatusMenu(int playerMaxHP, int enemyMaxHP, String pBPName, String eBPName) {
        // Need 2 healthbars
        playerBar = new AMenuFillableBar(
                600, 20, 10, 100, playerMaxHP, Color.green, false
        );
        enemyBar = new AMenuFillableBar(
                600, 120, 10, 100, enemyMaxHP, Color.green, false
        );
        displayItems.add(playerBar);
        displayItems.add(enemyBar);
        // We also need 2 text items to display the names
        AMenuTextItem playerText = new AMenuTextItem(pBPName, 600, 18, Color.black, textFont);
        AMenuTextItem enemyText = new AMenuTextItem(eBPName, 600, 118, Color.black, textFont);
        displayItems.add(playerText);
        displayItems.add(enemyText);
    }

    public void setBarValues(int playerHP, int enemyHP) {
        playerBar.setValue(playerHP);
        enemyBar.setValue(enemyHP);
    }

}
