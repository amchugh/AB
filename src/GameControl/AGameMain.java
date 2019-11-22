package GameControl;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class AGameMain {

  private ADisplay display;
  private ASettings settings;
  private AController controller;

  private boolean startWithMap = false;
  
  private AScene currentScene;

  // Resource Managers
  private AEncounterEnvironmentManager environmentManager;
  private ABPSpeciesManager speciesManager;
  private ABPActionManager actionManager;

  // Resource File Locations
  private static final String DEFAULT_ENVIRONMENT_RESOURCE = "rsc/SimpleEnvironmentData.eef";
  private static final String DEFAULT_SPECIES_RESOURCE = "rsc/SimpleSpeciesData.sdf";
  private static final String DEFAULT_ACTION_RESOURCE = "rsc/SimpleActionData.adf";
  private static final String DEFAULT_MAP_RESOURCE = "rsc/TestMap.map";

  private String environmentResource = DEFAULT_ENVIRONMENT_RESOURCE;
  private String speciesResource = DEFAULT_SPECIES_RESOURCE;
  private String actionResource = DEFAULT_ACTION_RESOURCE;

  public AGameMain() {
  }

  public void setStartWithMap(boolean startWithMap) {
    this.startWithMap = startWithMap;
  }

  public void setEnvironmentResourceName(String s) {
    environmentResource = s;
  }

  public void setActionResourceName(String s) {
    actionResource = s;
  }

  public void setSpeciesResourceName(String s) {
    speciesResource = s;
  }

  public boolean areResourcesLoaded() {
    return environmentManager != null && speciesManager != null && actionManager != null;
  }

  public void go() {
    // Grab the settings for the game
    settings = ASettings.DEFAULT_SETTINGS; // TODO load settings

    // Load all the resources
    try {
      loadResources();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Create the display
    display = new ADisplay(settings.getWindowSize());

    // Add the controller
    controller = new AController();
    display.addKeyListener(controller);

    // Set the current scene
    APlayerCharacter p = new APlayerCharacter();
    AEnemy e = new AEnemy();
    AEncounterEnvironment e2 = new AEncounterEnvironment(0);

    if (startWithMap) {
      try {
        ACellManager cellManager = new ACellManagerSimple();
        AMap map = new AMapReader(DEFAULT_MAP_RESOURCE).constructMap(cellManager);
        AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
        map.setViewAdvisor(viewAdvisor);
        currentScene = map.getScene();
      } catch (Exception ex) {
        throw new RuntimeException("Unexpected", ex);
      }
    } else {
      currentScene = new AEncounter(p, e, e2);
    }

    loop();
  }

  public void loadResources() throws IOException, ParseException {
    AEncounterEnvironmentManagerReader er = new AEncounterEnvironmentManagerReader(environmentResource);
    environmentManager = er.initializeEnvironmentManager();
    ABPActionManagerReader ar = new ABPActionManagerReader(actionResource);
    actionManager = ar.initializeActionManager();
    ABPSpeciesManagerReader sr = new ABPSpeciesManagerReader(speciesResource);
    speciesManager = sr.initializeSpeciesManager();
  }

  public void loop() {
    // make the display visible
    display.setVisible(true);

    while (true) {
      // We update
      currentScene.update();
  
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
