package GameControl;

import java.awt.*;

/**
 * The class that all encounters and maps are based off of
 */
public interface AScene {
  
  void update();
  void draw(Graphics g);
  
  // Because of the way input works, it needs to be passed from AGameMain
  void onButtonPress(char button);
  
}
