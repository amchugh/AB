package GameControl;

public class AGameMain {
  
  private ADisplay display;
  private ASettings settings;
  
  private AScene currentScene;
  
  public AGameMain() {
    // Grab the settings for the game
    settings = ASettings.DEFAULT_SETTINGS; // TODO load settings
    // Create the display
    display = new ADisplay(settings.getWindowSize());
    
    // Set the current scene
    currentScene = new AMap();
    
    // Finally, make the display visible
    display.setVisible(true);
  }
  
  public void loop() {
    
    while (true) {
    
    
    
    }
    
  }
  
}
