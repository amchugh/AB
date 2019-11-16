package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ABPSpecies {

  public Image frontImage;
  public Image backImage;
  
  // TODO this class also needs work.
  public ABPSpecies() {
    try {
      frontImage = ImageIO.read(new File("images/planetEv1.png"));
      backImage = ImageIO.read(new File("images/TourcisEv1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
