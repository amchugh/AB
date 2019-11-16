package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AEncounterEnvironment {
  
  private Image backgroundImage;
  
  public AEncounterEnvironment() {
    try {
      backgroundImage = ImageIO.read(new File("images/planetEv1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }  }
  
  public AEncounterEnvironment(Image background) {
    backgroundImage = background;
  }
  
  public Image getBackgroundImage() {
    return backgroundImage;
  }
  
}
