package GameControl;

import java.awt.*;

// This is the interface definition for a Map instance.  A Map represents an object which is updated every step
// and which can be drawn onto the display.  This is a very generic definition.
//
// TODO:  This is too generic and really represents a "I am a generic thing that can be displayed".  It is likely
// TODO:  that this AMap will be augmented in the future to make it more specific.  In case it doesn't we should
// TODO:  replace with a more generic interface name.

public interface AMap {

  // Set the focus point for the map as a grid position.  The AMap implementation will decide where
  // this focus point is rendered on the screen based on characteristics of the map and environment.
  // Since it is the focus point it is likely that the AMap implementation will place this at the
  // center of the viewing area.
  void setViewFocus(GridPos pos);

  void update();
  void draw(Graphics g);
}
