package GameControl;

import java.awt.*;
import java.util.ArrayList;

/**
 * A concrete instance of AScene that handles the orchestration of a battle between one player and an enemy.
 */
public class ASceneEncounter implements AScene {
  
  private APlayerCharacter player; // The player object
  private AEncounterInstance encounter; // The other data of the encounter
  private AEncounterStatusMenu statusMenu;
  private AMenuManager menu;
  private AEncounterController encounterController;

  private boolean isSetup;
  private boolean hasShownDeathMessage = false;
  private boolean encounterOver;

  // This enum/field will be used to store the current state of the encounter.
  // For example, is the user selecting a move, is narration being displayed, etc
  private enum GameState {SELECTING_MOVE, TURN, DEATH_NARRATION, ENEMY_SWITCH_BP};
  private GameState currentState;

  private class Turn {
    boolean hasStarted = false;
    boolean hasFirstFinished = false;
    boolean isPlayerFirst;
    ABPAction playerAction;
    ABPAction enemyAction;
    private Turn(ABPAction p, ABPAction e) {
      playerAction = p; enemyAction = e;
      // todo:: calculate the first action based off of the speed
    }
    private Turn(ABPAction p, ABPAction e, boolean forcedPlayerFirst) {
      playerAction = p; enemyAction = e; isPlayerFirst = forcedPlayerFirst;
    }
  }

  private Turn currentTurn;

  public ASceneEncounter(APlayerCharacter player, AEncounterInstance encounter, AEncounterController encControler) {
    // Assign references
    this.player = player;
    this.encounter = encounter;
    this.encounterController = encControler;
    isSetup = false; encounterOver = false;
    currentState = GameState.SELECTING_MOVE;
  }

  public void setup() {
    // Get the BP's set
    encounter.getEnemy().onBattleStart();
    player.onBattleStart();
    
    makeMenu();
    remakeStatusMenu();
    isSetup = true;
  }

  private void remakeStatusMenu() {
    ABP p = player.getActiveBP();
    ABP e = encounter.getEnemy().getActiveBP();
    statusMenu = new AEncounterStatusMenu(
            p.getMaxHealth(),
            e.getMaxHealth(),
            p.getName(),
            e.getName());
    updateStatusMenu();
  }

  private void updateStatusMenu() {
    statusMenu.updateHealthValues(player.getActiveBP().getHealth(), encounter.getEnemy().getActiveBP().getHealth());
  }

  private void makeMenu() {
    switch (currentState) {
      case DEATH_NARRATION: makeDeathAnimationMenu(); break;
      case TURN: break; // Do nothing as action menus need to be created slightly differently
      case SELECTING_MOVE: makeActionMenu(); break;
      case ENEMY_SWITCH_BP: makeEnemySwitchMenu(); break;
    }
  }
  
  private void makeActionNarrationMenu(String[] text) {
    menu = new AEncounterNarrationMenu(text);
  }

  private void makeEnemySwitchMenu() {
    menu = new AEncounterNarrationMenu("Enemy sent out " + encounter.getEnemy().getActiveBP().getName() + "!");
  }

  private void makeDeathAnimationMenu() {
    String deathText;
    if (encounter.getEnemy().hasMoreBP()) {
      // The enemy is still alive
      deathText = "You lost :(";
    } else {
      deathText = encounter.getEnemy().getDeathText();
    }
    menu = new AEncounterNarrationMenu(deathText);
    hasShownDeathMessage = true;
  }

  private void makeActionMenu() {
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

    switch(currentState) {
      case SELECTING_MOVE: handleMoveSelection(); break;
      case DEATH_NARRATION: handleDeathNarration(); break;
      case TURN: handleTurnNarration(); break;
      case ENEMY_SWITCH_BP: handleEnemySwitch(); break;
    }
  }

  private void handleEnemySwitch() {
    // Just wait for player input
    if (encounterController.doSelect()) {
      currentState = GameState.SELECTING_MOVE;
      // Build new menus and such
      makeMenu();
    }
  }

  private void handleTurnNarration() {
    if (!currentTurn.hasStarted) {
      // The turn just began, do the first action
      performNextAction(currentTurn);
      currentTurn.hasStarted = true;
    }

    // Now we wait for input
    if (encounterController.doSelect()) {
      // Make sure the current narration menu has displayed all of its text
      AEncounterNarrationMenu nm = (AEncounterNarrationMenu) menu;
      if (nm.hasMoreTexts()) {
        nm.showNext();
      } else {
        // See if a enemy or player has died
        if (testForDeath()) {
          // Something just died, so we have new menus to show
          makeMenu();
          remakeStatusMenu();
        } else {
          // We either do the next action, or we end the turn and select our next move
          if (currentTurn.hasFirstFinished) {
            // Turn over, go back to move selection
            currentState = GameState.SELECTING_MOVE;
            // Update menu
            makeMenu();
          } else {
            // We need to perform the other action
            currentTurn.hasFirstFinished = true;
            performNextAction(currentTurn);
          }
        }
      }
    }
  }

  private void performNextAction(Turn turn) {
    assert currentState == GameState.TURN;

    ABP pbp = player.getActiveBP();
    ABP ebp = encounter.getEnemy().getActiveBP();
    
    if (currentTurn.hasFirstFinished) {
      if (currentTurn.isPlayerFirst) {
        // player has already finished, do enemy action
        performAction(currentTurn.enemyAction, pbp, ebp);
      } else {
        performAction(currentTurn.playerAction, ebp, pbp);
      }
    } else {
      if (currentTurn.isPlayerFirst) {
        // First has not finished, and the player is first, so the player goes now
        performAction(currentTurn.playerAction, ebp, pbp);
      } else {
        performAction(currentTurn.enemyAction, pbp, ebp);
      }
    }
  }

  private void performAction(ABPAction attackingAction, ABP target, ABP caster) {
    // Perform the action on the target
    // todo:: add calculation for finding the critical chance. Right now, no attack will ever crit.
    attackingAction.performActions(caster, target, false);
    // Setup the display
    String[] display;
    if (target.isBPWeak(attackingAction.getType())) {
      display = new String[2];
      display[1] = "It's super effective!";
    } else {
      display = new String[1];
    }
    display[0] = caster.getName() + " used " + attackingAction.getName() + "!";
    // Display the move
    makeActionNarrationMenu(display);
    // Update the health bars
    updateStatusMenu();
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

      // Get the move from the enemy.
      ABPAction enemyAction = encounter.getEnemy().getMove();

      // Create the turn object
      currentTurn = new Turn(action, enemyAction, true); // todo:: remove the forced player first

      currentState = GameState.TURN;
    }

    // Check to see if the selection has changed
    AMenuFlowProvider.FlowDirection d = encounterController.currentDirectionForce();
    if (d != null) {
      menu.attemptMove(d);
    }
  }

  private boolean testForDeath() {
    // Test the enemy first
    if (testEnemyDeath())
      return true;
    // Player death will not be tested if the enemy already died
    // Then test the player death
    return testPlayerDeath();
  }

  /**
   * Test to see if the enemy has died and handles the state switch
   * @return if the current enemy BP was dead
   */
  private boolean testEnemyDeath() {
    boolean isAlive = encounter.getEnemy().isBPAlive();
    if (!isAlive) {
      if (encounter.getEnemy().hasMoreBP()) {
        // The enemy is still alive
        // Tell them to switch to a new BP
        encounter.getEnemy().switchBP();
        // Set the state so the narration gets displayed
        currentState = GameState.ENEMY_SWITCH_BP;
        return true;
      } else {
        // The enemy is defeated
        currentState = GameState.DEATH_NARRATION;
        return true;
      }
    }
    return false;
  }

  /**
   * Test to see if the player has died and handles the state switch
   * @return if the current player BP was dead
   */
  private boolean testPlayerDeath() {
    boolean isAlive = player.getActiveBP().isAlive();
    if (!isAlive) {
      if (player.getTeam().hasLivingBP()) {
        // The player has more BP available
        // todo:: add functionality
      } else {
        // The player has lost!
        currentState = GameState.DEATH_NARRATION;
        // update the menu
        makeMenu();
        return true;
      }
    }
    return false;
  }

  private void handleDeathNarration() {
    if (encounterController.doSelect()) {
      if (hasShownDeathMessage) {
        encounterOver = true;
      } else {
        makeMenu();
      }
    }
  }

  @Override
  public void draw(Graphics g) {
    Dimension size = ASettings.getCurrentSettings().getWindowSize();
    // First, draw the background image
    g.drawImage(encounter.getEnvironment().getBackgroundImage(), 0, 0, size.width, size.height,null);
    // Draw the menu
    menu.draw(g);
    statusMenu.draw(g);
    Dimension bpImageSize = new Dimension(128,128);
    // Draw the active BP's
    g.drawImage(
            player.getActiveBP().getSpecies().getFrontImage(),
            size.width/2 - bpImageSize.width/2, (int)Math.floor(size.height*.8) - bpImageSize.height/2 - 10,
            bpImageSize.width, bpImageSize.height, null);
    g.drawImage(
            encounter.getEnemy().getActiveBP().getSpecies().getBackImage(),
            size.width/2 - bpImageSize.width/2, (int)Math.floor(size.height*.2) - bpImageSize.height/2,
            bpImageSize.width, bpImageSize.height, null);
  }

  @Override
  public boolean shouldScenePop() {
    return encounterOver;
  }

  @Override
  public ASceneData shouldPushScene() {
    // Encounters will likely never push new scenes
    return null;
  }
}
