package GameControl;

import java.awt.*;
import java.util.List;

public class AMapInstance implements AMap, AScene, AGridPosValidator {
    private int id;
    private int gridWidth;
    private int gridHeight;
    private int [][] cells;
    private GridPos desiredCenter;
    private ACellManager aCellManager;
    private AViewAdvisor viewAdvisor;

    AMapInstance(int id, int gridWidth, int gridHeight, ACellManager aCellManager) {
        this.id = id;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.aCellManager = aCellManager;

        desiredCenter = new GridPos(0,0);
        cells = new int[gridHeight][gridWidth];
    }

    public void setCell(int row, int col, int cell) {
        assert(row < gridHeight);
        assert(col < gridWidth);
        assert (aCellManager.isCellIdValid(cell));

        cells[row][col] = cell;
    }

    @Override
    public void setViewFocus(GridPos pos) {
        desiredCenter = pos;
    }

    @Override
    public void setViewAdvisor(AViewAdvisor advisor) {
        this.viewAdvisor = advisor;
    }

    @Override
    public AScene getScene() {
        return this;
    }

    @Override
    public AGridPosValidator getGridPosValidator() {
        return this;
    }

    // AMap methods
    @Override
    public void step() {

    }

    @Override
    public void draw(Graphics g) {
        // TODO:  Need to figure out how large the graphics region is to draw into
        int viewableWidth = 5;
        int viewableHeight = 5;

        List<AViewAdvisor.ViewableGridCell> viewableCells =
                viewAdvisor.advise(desiredCenter, viewableWidth, viewableHeight, gridWidth, gridHeight);

        int cellWidth = aCellManager.getCellWidth();
        int cellHeight = aCellManager.getCellHeight();

        for (AViewAdvisor.ViewableGridCell c : viewableCells) {
            int graphicsX = c.screenCoordinate.x * cellWidth;
            int graphicsY = c.screenCoordinate.y * cellHeight;
            int cellId = cells[c.gridPos.getY()][c.gridPos.getX()];
            g.drawImage(aCellManager.getCellImage(cellId), graphicsX, graphicsY, cellWidth, cellHeight, null);
        }
    }

    @Override
    public boolean isGridPosOccupiable(GridPos g) {
        if (g.getX() < 0 || g.getY() < 0) {
            return false;
        }
        if (g.getX() >= gridWidth || g.getY() >= gridHeight) {
            return false;
        }
        return true;
    }
}
