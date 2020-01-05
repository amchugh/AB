package GameControl;

import java.awt.*;

// An AMap is a construct that orchestrates the overarching and main view into the "physical world" that the
// player is operating in.  A map is logically a collection of cells arranged as a grid.  The rendering of the
// discrete units (the cells) is the responsibility of the AScene entity which can be accessed through
// this interface.
public interface AMap {

  // Set the focus point for the map as a grid position.  The AMap implementation will decide where
  // this focus point is rendered on the screen based on characteristics of the map and environment.
  // Since it is the focus point it is likely that the AMap implementation will place this at the
  // center of the viewing area.
  void setViewFocus(GridPos pos);

  // Set the view advisor which is used to determine which map cells can be currently seen.
  void setViewAdvisor(AViewAdvisor advisor);

  // A map must be stepped.
  void step();

  // A map can draw onto a graphics region.
  void draw(Graphics g, Rectangle bounds);

  AGridPosValidator getGridPosValidator();
}
