package GameControl;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AMapInstance implements AMap, AGridPosValidator {
    private int id;
    private int gridWidth;
    private int gridHeight;
    private int [][] cells;
    private GridPos desiredCenter;
    private ACellManager aCellManager;
    private AViewAdvisor viewAdvisor;
    private boolean highlightDesiredCenter;
    private List<ARegionEncounterGenerator> regions;
    private List<ARegionEncounterGenerator> containingRegions;
    private ARegionEncounterGenerator lastRegionEncounterGenerator;

    AMapInstance(int id, int gridWidth, int gridHeight, ACellManager aCellManager) {
        this.id = id;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.aCellManager = aCellManager;

        desiredCenter = new GridPos(0,0);
        cells = new int[gridHeight][gridWidth];
        regions = new ArrayList<>();
        lastRegionEncounterGenerator = null;
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
    public AGridPosValidator getGridPosValidator() {
        return this;
    }

    public void setHighlightDesiredCenter(boolean highlightDesiredCenter) {
        this.highlightDesiredCenter = highlightDesiredCenter;
    }

    // AMap methods
    @Override
    public void step() {
        List<ARegionEncounterGenerator> curStepRegions = new ArrayList<>();
        for (ARegionEncounterGenerator r : regions) {
            if (r.containsGridPos(desiredCenter)) {
                curStepRegions.add(r);
            }
        }
        if (lastRegionEncounterGenerator != null && !curStepRegions.contains(lastRegionEncounterGenerator)) {
            lastRegionEncounterGenerator = null;
        }

        containingRegions = curStepRegions;
    }

    public ASceneData shouldIntroduceNewScene() {
        for (ARegionEncounterGenerator r : containingRegions) {
            if (r != lastRegionEncounterGenerator) {
                ASceneData d = r.shouldPushScene();
                if (d != null) {
                    System.out.println("found a new scene; " +
                            "region area: " + r.getTopLeft().getX() + ", " + r.getTopLeft().getY() + " -> " + r.getBottomRight().getX() + ", " + r.getBottomRight().getY());
                    lastRegionEncounterGenerator = r;
                    return d;
                }
            }
        }
        return null;
    }

    @Override
    public void draw(Graphics g, Rectangle bounds) {
        int cellWidth = aCellManager.getCellWidth();
        int cellHeight = aCellManager.getCellHeight();

        int desiredCols = bounds.width / cellWidth;
        int desiredRows = bounds.height / cellHeight;

        List<AViewAdvisor.ViewableGridCell> viewableCells =
                viewAdvisor.advise(desiredCenter, desiredRows, desiredCols, gridWidth, gridHeight);

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
        System.out.println("Adding region; " + aRegionEncounterGenerator.getTopLeft().getX() + ", " + aRegionEncounterGenerator.getTopLeft().getY() + " -> " + aRegionEncounterGenerator.getBottomRight().getX() + ", " + aRegionEncounterGenerator.getBottomRight().getY());
        regions.add(aRegionEncounterGenerator);
    }

    public List<ARegionEncounterGenerator> getRegions() {
        return regions;
    }
}
