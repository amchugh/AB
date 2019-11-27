package GameControl;

import java.awt.*;

/**
 * A concrete instance of AScene that handles the orchestration of a battle between one player and an enemy.
 */
public class ASceneEncounter implements AScene {
  
  private APlayerCharacter player; // The player object
  private AEncounterInstance encounter; // The other data of the encounter

  public ASceneEncounter(APlayerCharacter player, AEncounterInstance encounter) {
    this.player = player;
    this.encounter = encounter;
  }
  
  @Override
  public void step() {
  
  }
  
  @Override
  public void draw(Graphics g) {
    // First, draw the background image
    Dimension size = ASettings.getCurrentSettings().getWindowSize();
    g.drawImage(encounter.getEnvironment().getBackgroundImage(), 0, 0, size.width, size.height,null);
    // Draw the active BP's
    g.drawImage(player.getActiveBP().getSpecies().getFrontImage(), 10, 10, 100, 100, null);
    g.drawImage(encounter.getEnemy().getActiveBP().getSpecies().getBackImage(), 110, 10, 100, 100, null);
  }
}
