package GameControl;

import java.awt.*;

/**
 * A Scene is a basic unit of control within the game.  There is only a single scene which is controlling
 * the player's current experience within a game although a set of scenes may exist within a stack that is maintained
 * that provides transitions and context for what is happening in the game.
 *
 * A scene is stepped and drawn for the player.  A scene may decide that the specific experience is complete and
 * that the scene is done.  Finally a scene may decide that another scene should replace the current experience.
 */
public interface AScene {
  void step();
  void draw(Graphics g);
  boolean shouldScenePop();
  ASceneData shouldPushScene();
}

