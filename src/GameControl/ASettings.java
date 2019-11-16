package GameControl;

import java.awt.*;

public class ASettings {
  
  public static final ASettings DEFAULT_SETTINGS = new ASettings();
  
  private Dimension windowSize;
  
  public ASettings() {
    windowSize = new Dimension(600,600);
  }
  
  public Dimension getWindowSize() {
    return windowSize;
  }
  
}
