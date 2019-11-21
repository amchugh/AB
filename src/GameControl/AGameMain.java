package GameControl;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class AGameMain {
  
  private ADisplay display;
  private ASettings settings;
  private AController controller;
  
  private AScene currentScene;

  // Resource Managers
  private AEncounterEnvironmentManager environmentManager;
  private ABPSpeciesManager speciesManager;
  private ABPActionManager actionManager;

  // Resource File Locations
  private static final String  environmentResources = "rsc/SimpleEnvironmentData.eef";
  private static final String  speciesResources = "rsc/SimpleSpeciesData.sdf";
  private static final String  actionResources = "rsc/SimpleSpeciesData.sdf";

  public AGameMain() {
    this(true);
  }

  public AGameMain(boolean doSetup) {
    if (doSetup) {
      // Grab the settings for the game
      settings = ASettings.DEFAULT_SETTINGS; // TODO load settings

      // Load all the resources
      try {
        loadResources(environmentResources, speciesResources, actionResources);
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
      currentScene = new AEncounter(p, e, e2);
    }
  }

  public void loadResources(String environmentResources, String actionResources, String speciesResources) throws IOException, ParseException {
    AEncounterEnvironmentManagerReader er = new AEncounterEnvironmentManagerReader(environmentResources);
    environmentManager = er.initializeEnvironmentManager();
    ABPActionManagerReader ar = new ABPActionManagerReader(actionResources);
    actionManager = ar.initializeActionManager();
    ABPSpeciesManagerReader sr = new ABPSpeciesManagerReader(speciesResources);
    speciesManager = sr.initializeSpeciesManager();
  }

  public boolean areResourcesLoaded() {
    if (environmentManager == null) return false;
    if (actionManager == null) return false;
    if (speciesManager == null) return false;
    return true;
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
