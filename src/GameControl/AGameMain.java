package GameControl;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class AGameMain {

  private ADisplay display;
  private ASettings settings;
  private AUserInput userInput;

  // Stack of Scenes
  private Stack<AScene> sceneStack;

  // Resource Managers
  private AEncounterEnvironmentManager environmentManager;
  private ABPSpeciesManager speciesManager;
  private ABPActionManager actionManager;
  private ABPTypeManager typeManager;
  private APlayerCharacter player;

  private boolean isReady = false;

  public static final Random random = new Random(0);

  public AGameMain() {
    sceneStack = new Stack<>();
    isReady = false;
  }

  /**
   * Given a string, determines what kind of scene it is then adds it to the scene stack
   * @param filename the location of the map to add
   */
  public void addSceneSmart(String filename) throws IOException, ParseException {
    assert isReady;
    ASceneData.SceneTypes t = getSceneTypeFromExtension(filename);
    AScene n;
    switch (t) {
      case ENCOUNTER:
        n = createEncounterFromID(filename);
        break;
      case MAP:
        n = createMapFromID(filename);
        break;
      default:
        throw new IllegalArgumentException("Must specify a valid Scene Type");
    }
    sceneStack.push(n);
  }

  /**
   * Loads a scene from it's appropriate manager and then pushes the new scene to the top of the stack
   * @param type the type of scene (map, encounter, menu, ... etc). This tells what manager to access
   * @param id the id number of the scene  to load
   */
  public void addScene(ASceneData.SceneTypes type, int id) throws ParseException, IOException {
    assert isReady;
    AScene n;
    switch (type) {
      case ENCOUNTER:
        // Create the encounter
        n = createEncounterFromID(getEncounterNameFromID(id));
        // Add the encounter controller
        AEncounterController ec = new AEncounterController(userInput);
        break;
      case MAP:
        // Create the map
        n = createMapFromID(getMapNameFromID(id));
        // Create the controller
        AMapController mc = new AMapController(userInput);
        // Register the controller with the player
        player.setDesiredMovementProvider(mc);
        break;
      default:
        throw new IllegalArgumentException("Must specify a valid Scene Type");
    }
    sceneStack.push(n);
  }

  private ASceneData.SceneTypes getSceneTypeFromExtension(String filename) {
    try {
      String end = filename.split("\\.")[1];
      switch (end) {
        case "map":
          return ASceneData.SceneTypes.MAP;
        case "esf":
          return ASceneData.SceneTypes.ENCOUNTER;
        default:
          throw new IllegalArgumentException("File does not have a valid scene extension (\"" + end + "\")");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid filename", e);
    }
  }

  private ASceneMap createMapFromID(String filename) throws IOException, ParseException {
    ACellManager cellManager = new ACellManagerSimple();
    AMap map = new AMapReader(filename).constructMap(cellManager);
    AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
    map.setViewAdvisor(viewAdvisor);
    // In the map view the player must be aware of valid movements that can occur.
    player.setGridPosValidator(map.getGridPosValidator());
    return new ASceneMap(player, map);
  }

  private ASceneEncounter createEncounterFromID(String filename) throws IOException, ParseException {
    AEncounterInstance i = new AEncounterInstanceReader(filename).loadEncounter(environmentManager, speciesManager, actionManager);
    AEncounterController e = new AEncounterController(userInput);
    return new ASceneEncounter(player, i, e);
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

  public void setup() {
    if (!areResourcesLoaded()) {
      throw new RuntimeException("Resources are not loaded");
    }
    assert !isReady;

    // Grab the settings for the game
    settings = ASettings.DEFAULT_SETTINGS; // TODO load settings

    // Create the display
    display = new ADisplay(settings.getWindowSize());

    // Create the user input object
    userInput = new AUserInput();
    // Add the map controller to the key listeners
    display.addKeyListener(userInput);

    // Create the player.
    player = new APlayerCharacter();

    // todo:: remove these lines. Just here temporarily to add a BP to the player.
    // todo:: this should be loaded in later down the road
    try {
      ABP playerBP = new ABPReader(getBPDataFileNameFromID(0)).readABP(speciesManager, actionManager);
      player.addBP(playerBP);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Set our setup flag
    isReady = true;
  }

  public void loop() {
    // Ensure the GameMain is already setup
    assert isReady;

    // make the display visible
    display.setVisible(true);

    while (true) {

      // Step the major units within the game.
      player.step();
      sceneStack.peek().step();

      // See if a new scene should be pushed
      ASceneData d = sceneStack.peek().shouldPushScene();
      if (d != null) {
        try {
          addScene(d.type, d.id);
        } catch (ParseException e) {
          throw new RuntimeException("Failed to push new scene", e);
        } catch (IOException e) {
          throw new RuntimeException("Failed to push new scene", e);
        }
      }

      // See if the scene should be popped
      else if (sceneStack.peek().shouldScenePop()) {
        sceneStack.pop();
        if (sceneStack.empty()) {
          // Gameover!
          System.exit(0);
        }
      }

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

  public static String getMapNameFromID(int id) {
    return "rsc/Maps/" + id + ".map";
  }

  public static String getEncounterNameFromID(int id) {
    return "rsc/EncounterSceneFiles/" + id + ".esf";
  }

  public static String getBPDataFileNameFromID(int id) {
      return "rsc/BPFiles/" + id + ".bpf"; // TODO finish path
  }

  public static String getEnemyFileNameFromID(int id) {
      return "rsc/EnemyDataFiles/" + id + ".edf";
  }

  public static String getEnvironmentFileNameFromID(int id) {
      return "rsc/EncounterEnvironmentFiles/" + id + ".eef";
  }

}
