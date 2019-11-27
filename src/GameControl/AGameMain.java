package GameControl;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Stack;

public class AGameMain {

  public enum SceneTypes {ENCOUNTER, MAP};

  private ADisplay display;
  private ASettings settings;
  private AMapController controller;

  private static final String DEFAULT_MAP_RESOURCE = "rsc/TestMap.map";

  private boolean startWithMap = false;

  // Stack of Scenes
  private Stack<AScene> sceneStack;

  // Resource Managers
  private AEncounterEnvironmentManager environmentManager;
  private ABPSpeciesManager speciesManager;
  private ABPActionManager actionManager;
  private APlayerCharacter player;
  private ASceneMap mapScene;
  private ASceneEncounter encounterScene;

  public AGameMain() {
  }

  /**
   * Loads a scene from it's appropriate manager and then pushes the new scene to the top of the stack
   * @param type the type of scene (map, encounter, menu, ... etc). This tells what manager to access
   * @param id the id number of the scene  to load
   */
  public void addScene(SceneTypes type, int id) {

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
    AEncounterInstance i = new AEncounterInstance(0, e2, e);
    encounterScene = new ASceneEncounter(player, i);

    // Set the current scene depending on the flag passed in.
    if (startWithMap) {
      sceneStack.push(mapScene);
    } else {
      sceneStack.push(encounterScene);
    }

    loop();
  }

  public void loop() {
    // make the display visible
    display.setVisible(true);

    while (true) {

      // Step the major units within the game.
      player.step();
      sceneStack.peek().step();
  
      // Then we draw
      BufferStrategy b = display.canvas.getBufferStrategy();
      if (b == null) {
        display.canvas.createBufferStrategy(2);
        b = display.canvas.getBufferStrategy();
      }
  
      Graphics g = b.getDrawGraphics();

      sceneStack.peek().draw(g);
  
      // All drawing needs to happen before these lines.
      g.dispose();
      b.show();
    }
  }
  
  public void handleDraw() {
  }

  public static String getMapNameFromID(int id) {
    return id + ".map";
  }

  public static String getEncounterNameFromID(int id) {
    return id + ".eef";
  }

  public static String getBPDataFileNameFromID(int id) {return id + ".bpf"; }

  public static String getEnemyFileNameFromID(int id) {return id + ".edf";}

  public static String getEnvironmentFileNameFromID(int id) {return id + ".eef";}

}
