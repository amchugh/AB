package GameControl;

import java.awt.*;

public class ASettings {

  // Don't use this, only for testing really
  public static final ASettings DEFAULT_SETTINGS = new ASettings();

  // Reference this value instead
  private static ASettings active = DEFAULT_SETTINGS;

  public static ASettings getCurrentSettings() {
    return active;
  }

  private Dimension windowSize;
  
  public ASettings() {
    windowSize = new Dimension(800,600);
  }
  
  public Dimension getWindowSize() {
    return windowSize;
  }
  
}
