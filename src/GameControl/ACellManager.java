package GameControl;

import java.awt.*;

// This interface captures the concept of a manager of cells.  A cell is a visual unit that occupies a fixed
// number of pixels and has an image.  A cell is identified by an integer value.
// When rendering a scene the cell manager is used to get common elements which may be drawn many times in
// different physical locations on the window.
public interface ACellManager {
    public int getCellWidth();
    public int getCellHeight();
    public Image getCellImage(int cellId);

    public boolean isCellIdValid(int cellId);
}
