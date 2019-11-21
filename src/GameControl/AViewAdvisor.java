package GameControl;

import java.util.List;

// An implementation of the View advisor is used to determine which grid cells can be seen and where, logically,
// they will be drawn on the screen.
public interface AViewAdvisor {
    class LogicalScreenCoordinate {
        public LogicalScreenCoordinate() {
        }

        public LogicalScreenCoordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int x;
        public int y;
    }

    ;

    class ViewableGridCell {
        public ViewableGridCell(LogicalScreenCoordinate s, GridPos g) {
            this.screenCoordinate = s;
            this.gridPos = g;
        }

        public ViewableGridCell() {
        }

        LogicalScreenCoordinate screenCoordinate;
        GridPos gridPos;
    }

    /**
     * Determined the viewable cells.
     *
     * @param desiredCenter - a grid position that we would like at the center of the viewable region.
     * @param desiredRows   - the desired number of rows in the viewable area
     * @param desiredCols   - the desired number of columns in the viewable area
     * @param maxGridX
     * @param maxGridY
     * @return
     */
    // Passed in:
    // @arg desiredCenter -
    // Given a grid position which we would like to be at the center of the viewable area, along
    // with the maximum logical screen x and y along with the maximum Grid cells in x and y, and
    // finally the desired number of rows and cells.
    List<ViewableGridCell> advise(GridPos desiredCenter,
                                  int desiredRows, int desiredCols,
                                  int maxGridX, int maxGridY);
}
