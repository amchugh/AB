package GameControl;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AMapInstance implements AMap, AScene, AGridPosValidator {
    private int id;
    private int gridWidth;
    private int gridHeight;
    private int [][] cells;
    private GridPos desiredCenter;
    private ACellManager aCellManager;
    private AViewAdvisor viewAdvisor;
    private boolean highlightDesiredCenter;
    private List<ARegionEncounterGenerator> regions;


    AMapInstance(int id, int gridWidth, int gridHeight, ACellManager aCellManager) {
        this.id = id;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.aCellManager = aCellManager;

        desiredCenter = new GridPos(0,0);
        cells = new int[gridHeight][gridWidth];
        regions = new ArrayList<>();
    }

    public int getGridId() {
        return id;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getCell(int row, int col) {
        assert (row < gridHeight);
        assert (col < gridWidth);

        return cells[row][col];
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

    public void setHighlightDesiredCenter(boolean highlightDesiredCenter) {
        this.highlightDesiredCenter = highlightDesiredCenter;
    }

    // AMap methods
    @Override
    public void step() {

    }

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
            if (highlightDesiredCenter && c.gridPos.getX() == desiredCenter.getX() && c.gridPos.getY() == desiredCenter.getY()) {
                g.drawRect(graphicsX, graphicsY, cellWidth, cellHeight);
            }
        }
    }

    @Override
    public boolean shouldScenePop() {
        return false; // readme:: i don't know if this class should implement AScene and AMap...
    }

    @Override
    public ASceneData shouldPushScene() {
        return null;
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

    public void addRegion(ARegionEncounterGenerator aRegionEncounterGenerator) {
        regions.add(aRegionEncounterGenerator);
    }
}
