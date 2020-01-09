package GameControl;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.Random;
import java.util.Stack;

public class AGameMain {

  private ADisplay display;
  private ASettings settings;
  private AUserInput userInput;

  // Stack of Scenes
  private Stack<AScene> sceneStack;

  // Resource Managers
  private AResourceManager<AEncounterEnvironment> environmentManager;
  private AResourceManager<ABPSpecies> speciesManager;
  private AResourceManager<ABPAction> actionManager;
  private AResourceManager<ABPType> typeManager;
  private APlayerCharacter player;
  private APlayerMapAvatar playerMapAvatar;

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
    addScene(type, id, false);
  }

  /**
   * Loads a scene from it's appropriate manager and then pushes the new scene to the top of the stack
   * @param type the type of scene (map, encounter, menu, ... etc). This tells what manager to access
   * @param id the id number of the scene  to load
   * @param doUpdate should the scene be updated as part of adding it
   */
  public void addScene(ASceneData.SceneTypes type, int id, boolean doUpdate) throws ParseException, IOException {
    assert isReady;
    sceneStack.push(
            switch (type) {
              case ENCOUNTER -> createEncounterFromID(getEncounterNameFromID(id));
              case MAP -> createMapFromID(getMapNameFromID(id));
              case SPLASH -> createSplashSceneForMapId(id);
              default -> throw new UnexpectedException("Unexpected!");
            }
    );
    // We need to step the new scene once to allow it to be ready for the upcoming draw cycle
    if (doUpdate) {
      sceneStack.peek().step();
    }
  }

  private ASceneData.SceneTypes getSceneTypeFromExtension(String filename) {
    String[] parts = filename.split("\\.");
    if (parts.length <= 1) {
      throw new IllegalArgumentException("Filename does not contain an exception");
    }

    return switch (parts[1]) {
      case "map" -> ASceneData.SceneTypes.MAP;
      case "esf" -> ASceneData.SceneTypes.ENCOUNTER;
      default -> throw new IllegalArgumentException("Unexpected extension");
    };
  }

  private ASceneMap createMapFromID(String filename) throws IOException, ParseException {
    // ACellManager cellManager = new ACellManagerSpriteSheet("rsc/images/map/world_map_tiles.png",
    //         16, 16, 4, 50, 1, 1);
    ACellManager cellManager = new ACellManagerSpriteSheet("rsc/images/map/kellen.png",
            32, 32, 37, 1, 0, 0);
    AMap map = new AMapReader(filename).constructMap(cellManager);
    AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
    map.setViewAdvisor(viewAdvisor);
    // In the map view the player must be aware of valid movements that can occur.
    playerMapAvatar.setGridPosValidator(map.getGridPosValidator());
    return new ASceneMap(playerMapAvatar, map, new Rectangle(0, 0,
            settings.getWindowSize().width, settings.getWindowSize().height));
  }

  private ASceneSplashScreen createSplashSceneForMapId(int mapId) throws IOException, ParseException {
    return new ASceneSplashScreen(userInput, mapId);
  }
  private ASceneEncounter createEncounterFromID(String filename) throws IOException, ParseException {
    AEncounterInstance i = new AEncounterInstanceReader(filename).loadEncounter(environmentManager, speciesManager, actionManager);
    AEncounterController e = new AEncounterController(userInput);
    return new ASceneEncounter(player, i, e);
  }

  public void setEncounterEnvironmentManager(AResourceManager<AEncounterEnvironment> m) {
    environmentManager = m;
  }

  public void setBPActionManager(AResourceManager<ABPAction> m) {
    actionManager = m;
  }

  public void setBPSpeciesManager(AResourceManager<ABPSpecies> m) {
    speciesManager = m;
  }
  
  public void setBPTypeManager(AResourceManager<ABPType> m) {
    typeManager = m;
  }

  public boolean areResourcesLoaded() {
    return environmentManager != null && speciesManager != null && actionManager != null;
  }

  public void setup() throws IOException {
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
    playerMapAvatar = new APlayerMapAvatar(player, new AMapController(userInput));

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
      playerMapAvatar.step();
      sceneStack.peek().step();

      // See if a new scene should be pushed
      ASceneData d = sceneStack.peek().shouldPushScene();
      if (d != null) {
        try {
          addScene(d.type, d.id, true);
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
      return "rsc/BPFiles/" + id + ".bpf";
  }

  public static String getEnemyFileNameFromID(int id) {
      return "rsc/EnemyDataFiles/" + id + ".edf";
  }

  public static String getEnvironmentFileNameFromID(int id) {
      return "rsc/EncounterEnvironmentFiles/" + id + ".eef";
  }

}
