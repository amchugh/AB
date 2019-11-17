package GameControl;

import java.awt.*;

// This is the interface definition for a Map instance.  A Map represents an object which is updated every step
// and which can be drawn onto the display.  This is a very generic definition.
//
// TODO:  This is too generic and really represents a "I am a generic thing that can be displayed".  It is likely
// TODO:  that this AMap will be augmented in the future to make it more specific.  In case it doesn't we should
// TODO:  replace with a more generic interface name.

public interface AMap {
  void update();
  void draw(Graphics g);
}
