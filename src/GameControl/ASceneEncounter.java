package GameControl;

import java.awt.*;
import java.util.ArrayList;

/**
 * A concrete instance of AScene that handles the orchestration of a battle between one player and an enemy.
 */
public class ASceneEncounter implements AScene {
  
  private APlayerCharacter player; // The player object
  private AEncounterInstance encounter; // The other data of the encounter
  private AMenuManager menu;
  private AEncounterController encounterController;

  private boolean isSetup;
  private boolean encounterOver;

  public ASceneEncounter(APlayerCharacter player, AEncounterInstance encounter, AEncounterController encControler) {
    // Assign references
    this.player = player;
    this.encounter = encounter;
    this.encounterController = encControler;
    isSetup = false; encounterOver = false;
  }

  public void setup() {
    makeMenu();
    isSetup = true;
  }

  private void makeMenu() {
    ABP t = player.getActiveBP();
    ArrayList<ABPAction> actions = t.getActions();
    String[] names = new String[actions.size()];
    for (int i = 0; i < actions.size(); i++) {
      names[i] = actions.get(i).getName();
    }
    menu = new AEncounterActionMenu(names);
  }
  
  @Override
  public void step() {
    // Make sure the scene is setup
    if(!isSetup)
      setup();

    handleMoveSelection();
  }

  private void handleMoveSelection() {
    // Check for selection
    if (encounterController.doSelect()) {
      // We've selected the current move
      int selectedAction = menu.getSelectedIndex();
      assert selectedAction < ABP.MAX_ACTIONS; // perhaps later we add more buttons to this screen,
                                               // but then we also need to edit this logic
      assert selectedAction >= 0;

      // Get the selected move from the bp
      ABPAction action = player.getActiveBP().getActions().get(selectedAction);

      // todo:: right now, the actions can only do damage. need to add the other possible action types
      encounter.getEnemy().takeDamage(action.getDamage());
      boolean isAlive = encounter.getEnemy().isBPAlive();
      if (!isAlive) {
        if (encounter.getEnemy().hasMoreBP()) {
          // The enemy is still alive
          // todo:: add support for switching to next BP on death
        } else {
          // The enemy is defeated
          System.out.println("Enemy defeated!");
          // todo:: add some text to the screen explaining the player has won.
          encounterOver = true;
        }
      }
    }

    // Check to see if the selection has changed
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

  @Override
  public boolean shouldScenePop() {
    return encounterOver;
  }

  @Override
  public ASceneData shouldPushScene() {
    return null;
  }
}
