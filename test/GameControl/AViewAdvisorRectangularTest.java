package GameControl;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AViewAdvisorRectangularTest {

    @Test
    void testViewablePositionCanBeCentered() {
        GridPos desiredCenter = new GridPos(3, 3);
        AViewAdvisorRectangular advisor = new AViewAdvisorRectangular();

        // Tell the advisor to determine the viewable cells with the center at 3,3 and
        // the viewable grid being of size 3x3.  Visually this should be:
        //
        //      0    1    2    3    4    5    6
        //   +----+----+----+----+----+----+----+
        // 0 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 1 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 2 |    |    | TL |  * | *  |    |    |
        //   +----+----+----+----+----+----+----+
        // 3 |    |    |  * |  C | *  |    |    |
        //   +----+----+----+----+----+----+----+
        // 4 |    |    |  * | *  | *  |    |    |
        //   +----+----+----+----+----+----+----+
        // 5 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 6 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        List<AViewAdvisor.ViewableGridCell> viewableCells =
                advisor.advise(desiredCenter, 3, 3, 7, 7);

        assertEquals(9, viewableCells.size());
        ensureViewableCellsLogicalScreenPosInRange(2, 2, viewableCells);
        ensureGridPosInRange(2, 2, 4, 4, viewableCells);
    }

    @Test
    void testViewablePositionPinnedTopLeft() {
        GridPos desiredCenter = new GridPos(0, 0);
        AViewAdvisorRectangular advisor = new AViewAdvisorRectangular();

        // Tell the advisor to determine the viewable cells with the center at 0,0 and
        // the viewable grid being of size 3x3.  Visually this should be:
        //
        //      0    1    2    3    4    5    6
        //   +----+----+----+----+----+----+----+
        // 0 | TL |  * | *  |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 1 |  * |  C | *  |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 2 |  * |  * | *  |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 3 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 4 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 5 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 6 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        List<AViewAdvisor.ViewableGridCell> viewableCells =
                advisor.advise(desiredCenter, 3, 3, 7, 7);

        assertEquals(9, viewableCells.size());
        ensureViewableCellsLogicalScreenPosInRange(2, 2, viewableCells);
        ensureGridPosInRange(0, 0, 2, 2, viewableCells);
    }

    @Test
    void testViewablePositionPinnedBottomRight() {
        GridPos desiredCenter = new GridPos(6, 6);
        AViewAdvisorRectangular advisor = new AViewAdvisorRectangular();

        // Tell the advisor to determine the viewable cells with the center at 6, 6 and
        // the viewable grid being of size 3x3.  Visually this should be:
        //
        //      0    1    2    3    4    5    6
        //   +----+----+----+----+----+----+----+
        // 0 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 1 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 2 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 3 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 4 |    |    |    |    | TL |  * |  * |
        //   +----+----+----+----+----+----+----+
        // 5 |    |    |    |    |  * |  * |  * |
        //   +----+----+----+----+----+----+----+
        // 6 |    |    |    |    |  * |  * |  * |
        //   +----+----+----+----+----+----+----+
        List<AViewAdvisor.ViewableGridCell> viewableCells =
                advisor.advise(desiredCenter, 3, 3, 7, 7);

        assertEquals(9, viewableCells.size());
        ensureViewableCellsLogicalScreenPosInRange(2, 2, viewableCells);
        ensureGridPosInRange(4, 4, 6, 6, viewableCells);
    }

    @Test
    void testNonSquareLowerRight() {
        GridPos desiredCenter = new GridPos(6, 6);
        AViewAdvisorRectangular advisor = new AViewAdvisorRectangular();

        // Tell the advisor to determine the viewable cells with the center at 6, 6 and
        // the viewable grid being of size 2x4.  Visually this should be:
        //
        //      0    1    2    3    4    5    6
        //   +----+----+----+----+----+----+----+
        // 0 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 1 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 2 |    |    |    |    |    |    |    |
        //   +----+----+----+----+----+----+----+
        // 3 |    |    |    |    |    | TL |  * |
        //   +----+----+----+----+----+----+----+
        // 4 |    |    |    |    |    |  * |  * |
        //   +----+----+----+----+----+----+----+
        // 5 |    |    |    |    |    |  * |  * |
        //   +----+----+----+----+----+----+----+
        // 6 |    |    |    |    |    |  * |  * |
        //   +----+----+----+----+----+----+----+
        List<AViewAdvisor.ViewableGridCell> viewableCells =
                advisor.advise(desiredCenter, 4, 2, 7, 7);

        assertEquals(8, viewableCells.size());
        ensureViewableCellsLogicalScreenPosInRange(1, 3, viewableCells);
        ensureGridPosInRange(5, 3, 6, 6, viewableCells);
    }

    // This functional interface is used to allow a lambda function to be defined over a viewable grid
    // cell.  This will allow reuse of the core logic of how to check the result independent of whether
    // we are looking at the logical screen coordinate or the grid cell itself.
    private interface ExtractCoordinate {
        int getCoordinate(AViewAdvisor.ViewableGridCell item);
    }

    private <T> void ensureCoordInRange(int minX, int minY, int maxX, int maxY,
                                        ExtractCoordinate getX,
                                        ExtractCoordinate getY,
                                        List<AViewAdvisor.ViewableGridCell> cells) {
        int expectedX = minX;
        int expectedY = minY;

        // There is nothing in the contract that the list must be in a specific order but I'm going to
        // enforce that anyway.
        for (AViewAdvisor.ViewableGridCell cell : cells) {
            if (expectedX > maxX) {
                expectedX = minX;
                expectedY += 1;
            }
            assertEquals(expectedX, getX.getCoordinate(cell));
            assertEquals(expectedY, getY.getCoordinate(cell));
            expectedX += 1;
        }
        assertTrue(expectedY == maxY, "Too many rows in the viewable set");
    }

    // min and max values are all inclusive
    private void ensureGridPosInRange(int minGridX, int minGridY, int maxGridX, int maxGridY,
                                      List<AViewAdvisor.ViewableGridCell> cells) {
        ensureCoordInRange(minGridX, minGridY, maxGridX, maxGridY,
                (c) -> c.gridPos.getX(),
                (c) -> c.gridPos.getY(),
                cells);
    }

    // min and max values are all inclusive
    private void ensureViewableCellsLogicalScreenPosInRange(int maxLogicalX, int maxLogicalY,
                                                            List<AViewAdvisor.ViewableGridCell> cells) {
        ensureCoordInRange(0, 0, maxLogicalX, maxLogicalY,
                (c) -> c.screenCoordinate.x,
                (c) -> c.screenCoordinate.y,
                cells);
    }
}