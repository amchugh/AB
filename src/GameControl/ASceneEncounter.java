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
  private enum GameState {SELECTING_MOVE, TURN, DEATH_NARRATION};
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
    makeMenu();
    initializeStatusMenu();
    updateStatusMenu();
    isSetup = true;
  }

  private void initializeStatusMenu() {
    ABP p = player.getActiveBP();
    ABP e = encounter.getEnemy().getActiveBP();
    statusMenu = new AEncounterStatusMenu(
            p.getMaxHealth(),
            e.getMaxHealth(),
            p.getName(),
            e.getName());
  }

  private void updateStatusMenu() {
    statusMenu.setBarValues(player.getActiveBP().getHealth(), encounter.getEnemy().getActiveBP().getHealth());
  }

  private void makeMenu() {
    switch (currentState) {
      case DEATH_NARRATION: makeDeathAnimationMenu(); break;
      case TURN:
      case SELECTING_MOVE: makeActionMenu(); break;
    }
  }

  private void makeActionNarrationMenu(String movename) {
    menu = new AEncounterNarrationMenu(movename);
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
      case TURN: handleTurn(); break;
    }
  }

  private void handleTurn() {
    if (!currentTurn.hasStarted) {
      // The turn just began, do the first action
      performNextAction(currentTurn);
      currentTurn.hasStarted = true;
    }

    // Now we wait for input
    if (encounterController.doSelect()) {
      // We either do the next action, or we end the turn and select our next move
      if (currentTurn.hasFirstFinished) {
        // todo:: remove debug console printing
        System.out.println("Player HP : " + player.getActiveBP().getHealth());
        System.out.println("Enemy HP  : " + encounter.getEnemy().getActiveBP().getHealth());
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

  private void performNextAction(Turn turn) {
    assert currentState == GameState.TURN;

    if (currentTurn.hasFirstFinished) {
      if (currentTurn.isPlayerFirst) {
        performEnemyAction(turn); // player has already finished, do enemy action
      } else {
        performPlayerAction(turn);
      }
    } else {
      if (currentTurn.isPlayerFirst) {
        performPlayerAction(turn); // player has already finished, do enemy action
      } else {
        performEnemyAction(turn);
      }
    }
  }

  private void performPlayerAction(Turn turn) {
    // Display the move
    makeActionNarrationMenu("Player used " + turn.playerAction.getName() + "!");
    // todo:: right now, the actions can only do damage. need to add the other possible action types
    // Deal damage
    enemyTakeDamage(turn.playerAction.getDamage());
  }

  private void performEnemyAction(Turn turn) {
    // Display the move
    makeActionNarrationMenu("Enemy used " + turn.enemyAction.getName() + "!");
    // todo:: right now, the actions can only do damage. need to add the other possible action types
    // Deal damage to the player
    playerTakeDamage(turn.enemyAction.getDamage());
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

  private void enemyTakeDamage(int damage) {
    encounter.getEnemy().takeDamage(damage);
    boolean isAlive = encounter.getEnemy().isBPAlive();
    if (!isAlive) {
      if (encounter.getEnemy().hasMoreBP()) {
        // The enemy is still alive
        // todo:: add support for switching to next BP on death
      } else {
        // The enemy is defeated
        currentState = GameState.DEATH_NARRATION;
        // We won't update the menu immediately.
      }
    }
    updateStatusMenu();
  }

  private void playerTakeDamage(int damage) {
    player.getActiveBP().takeDamage(damage);
    boolean isAlive = player.getActiveBP().isAlive();
    if (!isAlive) {
      if (player.getTeam().hasLivingBP()) {
        // The player has more guys available
        // todo:: add functionality
      } else {
        // The player has lost!
        currentState = GameState.DEATH_NARRATION;
        // update the menu
        makeMenu();
      }
    }
    updateStatusMenu();
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

    // todo:: remove. I'm going to draw a background plate for now just to make it clear what is being drawn at every frame
    g.setColor(Color.white);
    g.fillRect(0, 0, size.width, size.height);

    // First, draw the background image
    g.drawImage(encounter.getEnvironment().getBackgroundImage(), 0, 0, size.width, size.height,null);
    // Draw the menu
    menu.draw(g);
    statusMenu.draw(g);
    // Draw the active BP's
    g.drawImage(player.getActiveBP().getSpecies().getFrontImage(), 110, 10, 100, 100, null);
    g.drawImage(encounter.getEnemy().getActiveBP().getSpecies().getBackImage(), 210, 10, 100, 100, null);
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
