package GameControl;

import java.awt.*;

/**
 * This class encapsulates any screen that is shown to the user and for which UI input is passed to.
 * This is a generic interface and the concrete implementation is not important.  The raw smarts
 * of what is shown and how the user input is handled is captured here.
 */
public interface AScene {
  
  void update();
  void draw(Graphics g);
  
  // Because of the way input works, it needs to be passed from AGameMain
  void onButtonPress(char button);
  
}
