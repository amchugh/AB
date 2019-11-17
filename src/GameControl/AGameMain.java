package GameControl;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class AGameMain {
  
  private ADisplay display;
  private ASettings settings;
  private AController controller;
  
  private AScene currentScene;
  
  public AGameMain() {
    // Grab the settings for the game
    settings = ASettings.DEFAULT_SETTINGS; // TODO load settings
    // Create the display
    display = new ADisplay(settings.getWindowSize());

    // Add the controller
    controller = new AController();
    display.addKeyListener(controller);

    // Set the current scene
    APlayerCharacter p = new APlayerCharacter();
    AEnemy e = new AEnemy();
    AEncounterEnvironment e2 = new AEncounterEnvironment();
    currentScene = new AEncounter(p, e, e2);
    
    // Finally, make the display visible
    display.setVisible(true);
    
    loop();
  }
  
  public void loop() {
    
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
