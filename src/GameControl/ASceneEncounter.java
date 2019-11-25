package GameControl;

import java.awt.*;

/**
 * A concrete instance of AScene that handles the orchestration of a battle between one player and an enemy.
 */
public class ASceneEncounter implements AScene {
  
  private APlayerCharacter player; // The player object
  private AEnemy enemy; // The enemy of the encounter
  private AEncounterEnvironment environment; // holds info like music and background image

  public ASceneEncounter(APlayerCharacter _player, AEnemy _enemy, AEncounterEnvironment _environment) {
    player = _player;
    enemy = _enemy;
    environment = _environment;
  }
  
  @Override
  public void step() {
  
  }
  
  @Override
  public void draw(Graphics g) {
    // First, draw the background image
    Dimension size = ASettings.getCurrentSettings().getWindowSize();
    g.drawImage(environment.getBackgroundImage(), 0, 0, size.width, size.height,null);
    g.drawImage(player.getActiveBP().getSpecies().frontImage, 10, 10, 100, 100, null);
    g.drawImage(enemy.getActiveBP().getSpecies().backImage, 110, 10, 100, 100, null);
  }
}
