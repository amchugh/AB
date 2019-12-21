package GameControl;

import java.awt.*;

public class AEncounterStatusMenu extends AMenuManager {

    private static final Font textFont = new Font("Serif", Font.PLAIN, 24);
    private static final int barThickness = 10;
    private static final int textBuffer = 2;
    private static final Color textColor = Color.black;
    
    private AMenuFillableBar playerBar, enemyBar;
    private AMenuTextItem playerHPText;
    private int playerMaxHP;

    public AEncounterStatusMenu(int playerMaxHP, int enemyMaxHP, String pBPName, String eBPName) {
        // todo:: find a better way of setting these positions
        // Need 2 healthbars
        this.playerMaxHP = playerMaxHP;
        playerBar = new AMenuFillableBar(
            getPlayerUpperX(), getPlayerUpperY(), barThickness, 150, playerMaxHP, Color.green, false
        );
        enemyBar = new AMenuFillableBar(
            getEnemyUpperX(), getEnemyUpperY(), barThickness, 150, enemyMaxHP, Color.green, false
        );
        displayItems.add(playerBar);
        displayItems.add(enemyBar);
        // We also need 2 text items to display the names
        AMenuTextItem playerText = new AMenuTextItem(pBPName, getPlayerUpperX(), getPlayerUpperY()-textBuffer, textColor, textFont);
        AMenuTextItem enemyText = new AMenuTextItem(eBPName, getEnemyUpperX(), getEnemyUpperY()-textBuffer, textColor, textFont);
        displayItems.add(playerText);
        displayItems.add(enemyText);
        // We need another text item for the player health
        playerHPText = new AMenuTextItem("Health [placeholder, this should get overridden immediately",
            getPlayerUpperX(), getPlayerUpperY() + barThickness + textBuffer, textColor, textFont,
            false, true, false);
        displayItems.add(playerHPText);
    }
    
    private String getHealthText(int c_hp, int max_hp) {
        return "HP: " + c_hp + "/" + max_hp;
    }

    public void updateHealthValues(int playerHP, int enemyHP) {
        // Update the health bars
        setBarValues(playerHP, enemyHP);
        // Update the player hp text
        playerHPText.updateText(getHealthText(playerHP, playerMaxHP));
    }
    
    public void setBarValues(int playerHP, int enemyHP) {
        playerBar.setValue(playerHP);
        enemyBar.setValue(enemyHP);
    }

    // todo:: update these methods when we figure out how to handle multiple aspect ratios
    private int getEnemyUpperX() {
        return 350;
    }
    private int getEnemyUpperY() {
        return 40;
    }
    private int getPlayerUpperX() {
        return 350;
    }
    private int getPlayerUpperY() {
        return 540;
    }
    
}
