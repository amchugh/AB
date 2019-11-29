package GameControl;

import java.awt.*;

/**
 * This class encapsulates any screen that is shown to the user with basic 'step' and 'draw' methods.
 * Anything that wants to influence the way that things appear on the screen implements this interface.
 */
public interface AScene {
  void step();
  void draw(Graphics g);
  boolean shouldScenePop();
  ASceneData shouldPushScene();
}

