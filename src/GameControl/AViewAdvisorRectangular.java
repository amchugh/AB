package GameControl;

import java.util.ArrayList;
import java.util.List;

// This implementation of the view advisor displays a simple rectangular region around the desired position.
// An attempt is made to make the region be centered around the desired position such that this position
// is in the center of the logical screen.
public class AViewAdvisorRectangular implements AViewAdvisor {
    @Override
    public List<ViewableGridCell> advise(GridPos desiredCenter,
                                         int desiredRows, int desiredCols,
                                         int maxGridX, int maxGridY) {
        int centerLogicalScreenX = desiredCols / 2;
        int centerLogicalScreenY = desiredRows / 2;

        GridPos topLeft = new GridPos(
                Math.max(desiredCenter.getX() - centerLogicalScreenX, 0),
                Math.max(desiredCenter.getY() - centerLogicalScreenY, 0));

        // Two additional conditions to consider now.  If the topLeft is set
        // such that there won't be enough cells to the right in the grid to
        // fill the logical screen then we need to move the viewable window
        // to the left.  Similar condition for the bottom.
        if (topLeft.getX() + desiredCols > maxGridX) {
            topLeft = new GridPos(maxGridX - desiredCols + 1, topLeft.getY());
        }
        if (topLeft.getY() + desiredRows > maxGridY) {
            topLeft = new GridPos(topLeft.getX(), maxGridY - desiredRows + 1);
        }

        List<AViewAdvisorRectangular.ViewableGridCell> result = new ArrayList<>();
        for (int rows = 0; rows < desiredRows; rows++) {
            for (int cols = 0; cols < desiredCols; cols++) {
                ViewableGridCell v = new ViewableGridCell();
                v.screenCoordinate = new LogicalScreenCoordinate();
                v.screenCoordinate.x = cols;
                v.screenCoordinate.y = rows;
                v.gridPos = new GridPos(topLeft.getX() + cols, topLeft.getY() + rows);
                result.add(v);
            }
        }
        return result;
    }
}
