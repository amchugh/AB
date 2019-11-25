package GameControl;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class AGameMain {

  private ADisplay display;
  private ASettings settings;
  private AMapController controller;

  private static final String DEFAULT_MAP_RESOURCE = "rsc/TestMap.map";

  private boolean startWithMap = false;
  
  private AScene currentScene;

  // Resource Managers
  private AEncounterEnvironmentManager environmentManager;
  private ABPSpeciesManager speciesManager;
  private ABPActionManager actionManager;
  private APlayerCharacter player;
  private ASceneMap mapScene;
  private ASceneEncounter encounterScene;

  public AGameMain() {
  }

  public void setStartWithMap(boolean startWithMap) {
    this.startWithMap = startWithMap;
  }

  public void setEncounterEnvironmentManager(AEncounterEnvironmentManager m) {
    environmentManager = m;
  }

  public void setBPActionManager(ABPActionManager m) {
    actionManager = m;
  }

  public void setBPSpeciesManager(ABPSpeciesManager m) {
    speciesManager = m;
  }

  public boolean areResourcesLoaded() {
    return environmentManager != null && speciesManager != null && actionManager != null;
  }

  public void go() {
    assert environmentManager != null;
    assert speciesManager != null;
    assert actionManager != null;

    // Grab the settings for the game
    settings = ASettings.DEFAULT_SETTINGS; // TODO load settings

    // Create the display
    display = new ADisplay(settings.getWindowSize());

    // Add the controller
    controller = new AMapController();
    display.addKeyListener(controller);

    // Create the player.
    player = new APlayerCharacter();
    player.setDesiredMovementProvider(controller);

    // Wire the controller up into the player since the controls here will influence the
    // player position on the map.
    player.setDesiredMovementProvider(controller);

    try {
      ACellManager cellManager = new ACellManagerSimple();
      AMap map = new AMapReader(DEFAULT_MAP_RESOURCE).constructMap(cellManager);
      AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
      map.setViewAdvisor(viewAdvisor);
      // In the map view the player must be aware of valid movements that can occur.
      player.setGridPosValidator(map.getGridPosValidator());

      mapScene = new ASceneMap(player, map);
    } catch (Exception ex) {
      throw new RuntimeException("Unexpected", ex);
    }

    // TODO :: This is a placeholder and will normally be generated on the
    // fly.
    AEnemy e = new AEnemy();
    AEncounterEnvironment e2 = new AEncounterEnvironment(0);
    encounterScene = new ASceneEncounter(player, e, e2);

    // Set the current scene depending on the flag passed in.
    // TODO :: It is likely that we want to rework this.  You could imagine that
    // TODO :: the scenes are actually implemented as a stack with a scene being allowed to push
    // TODO :: a new scene onto the stack.  In this way you can be in a scene, like the main main
    // TODO :: scene or in a main menu scene, and it can say "I want a new scene to be pushed onto
    // TODO :: the stack" like a new encounter.  This scene is at the top of the stack until it says
    // TODO :: "I'm done" and then it is popped.
    // TODO :: The game main here would only step and draw the scene at the top of the stack.
    if (startWithMap) {
      currentScene = mapScene;
    } else {
      currentScene = encounterScene;
    }
    loop();
  }

  public void loop() {
    // make the display visible
    display.setVisible(true);

    while (true) {

      // Step the major units within the game.
      player.step();
      currentScene.step();
  
      // Then we draw
      BufferStrategy b = display.canvas.getBufferStrategy();
      if (b == null) {
        display.canvas.createBufferStrategy(2);
        b = display.canvas.getBufferStrategy();
      }
  
      Graphics g = b.getDrawGraphics();
  
      currentScene.draw(g);
  
      // All drawing needs to happen before these lines.
      g.dispose();
      b.show();
    }
  }
  
  public void handleDraw() {
  }
}
