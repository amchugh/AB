package GameControl;

import java.awt.*;

/**
 * A concrete instance of AScene that handles the orchestration of a battle between one player and an enemy.
 */
public class ASceneEncounter implements AScene {
  
  private APlayerCharacter player; // The player object
  private AEncounterInstance encounter; // The other data of the encounter
  private AMenuManager menu;
  private AEncounterController encounterController;

  public ASceneEncounter(APlayerCharacter player, AEncounterInstance encounter, AEncounterController encControler) {
    // Assign references
    this.player = player;
    this.encounter = encounter;
    this.encounterController = encControler;

    // Construct the menu
    menu = new AEncounterMenuManager();
  }
  
  @Override
  public void step() {
    AMenuFlowProvider.FlowDirection d = encounterController.currentDirectionForce();
    if (d != null) {
      menu.attemptMove(d);
    }
  }
  
  @Override
  public void draw(Graphics g) {
    // First, draw the background image
    Dimension size = ASettings.getCurrentSettings().getWindowSize();
    g.drawImage(encounter.getEnvironment().getBackgroundImage(), 0, 0, size.width, size.height,null);
    // Draw the menu
    menu.draw(g);
    // Draw the active BP's
    g.drawImage(player.getActiveBP().getSpecies().getFrontImage(), 10, 10, 100, 100, null);
    g.drawImage(encounter.getEnemy().getActiveBP().getSpecies().getBackImage(), 110, 10, 100, 100, null);
  }
}
