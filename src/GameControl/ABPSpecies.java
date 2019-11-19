package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ABPSpecies {

  private int idNumber;
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

  public ABPSpecies(int idNumber, String frontImageLocation, String backImageLocation) {
    this.idNumber = idNumber;
    try {
      frontImage = ImageIO.read(new File(frontImageLocation));
      backImage = ImageIO.read(new File(backImageLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
