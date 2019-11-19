package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ABPSpecies extends AIDItem {

  public Image frontImage;
  public Image backImage;
  
  public ABPSpecies() {
    super(0);
    try {
      frontImage = ImageIO.read(new File("rsc/images/planetEv1.png"));
      backImage = ImageIO.read(new File("rsc/images/TourcisEv1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ABPSpecies(int idNumber, String frontImageLocation, String backImageLocation) {
    super(idNumber);
    try {
      frontImage = ImageIO.read(new File(frontImageLocation));
      backImage = ImageIO.read(new File(backImageLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
