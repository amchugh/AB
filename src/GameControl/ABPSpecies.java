package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ABPSpecies extends AIDItem {

  private Image frontImage;
  private Image backImage;
  private int maxHealth;

  public ABPSpecies(int idNumber, String frontImageLocation, String backImageLocation, int maxHealth) {
    super(idNumber);
    this.maxHealth = maxHealth;
    loadImages(frontImageLocation, backImageLocation);
  }

  public void loadImages(String frontImageLocation, String backImageLocation) {
    try {
      frontImage = ImageIO.read(new File(frontImageLocation));
      backImage = ImageIO.read(new File(backImageLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Image getFrontImage() {
    return frontImage;
  }
  public Image getBackImage() {
    return backImage;
  }
  public int getMaxHealth() { return maxHealth; }

}
