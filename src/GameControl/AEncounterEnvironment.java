package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AEncounterEnvironment extends AIDItem {
  
  private Image backgroundImage;
  
  public AEncounterEnvironment(int id, Image background) {
    super(id);
    backgroundImage = background;
  }

  public AEncounterEnvironment(int id, String backgroundFileLocation) {
    super(id);
    try {
      backgroundImage = ImageIO.read(new File(backgroundFileLocation));
    } catch (IOException e) {
      throw new RuntimeException("Failed to load resource: " + backgroundFileLocation , e);
    }
  }
  
  public Image getBackgroundImage() {
    return backgroundImage;
  }
  
}
