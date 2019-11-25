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

    /**
     * A viewableGridCell encapsulates a logical position on the screen along with the
     * GridPosition in the map that should be drawn at that logical position.
     */
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
     * @param maxGridX      - maxGridX is the number of grid locations, in the x axis, that exist in the Grid.
     *                        the last valid cell index in the x dimension will always be maxGridX-1.
     * @param maxGridY      - maxGridY is the number of grid locations, in the y axis, that exist in the Grid.
     *                        the last valid cell index in the y dimension will always be maxGridY-1.
     * @return a list of ViewableGridCell positions that should be displayed.
     */
    List<ViewableGridCell> advise(GridPos desiredCenter,
                                  int desiredRows, int desiredCols,
                                  int maxGridX, int maxGridY);
}
