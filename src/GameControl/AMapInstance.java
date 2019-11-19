package GameControl;

import java.awt.*;

public class AMapInstance implements AMap, AScene {
    private int id;
    private int gridWidth;
    private int gridHeight;
    private int [][] cells;
    private GridPos desiredCenter;
    private ACellManager aCellManager;

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

    // AMap methods
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        // TODO:  Need to figure out how large the graphics region is to draw into
        int viewableWidth = 5;
        int viewableHeight = 5;

        GridPos topLeft = determineTopLeftPos();

        int cellWidth = aCellManager.getCellWidth();
        int cellHeight = aCellManager.getCellHeight();

        int graphicsX;
        int graphicsY = 0;

        for (int row = 0; row < viewableHeight; row++ ) {
            graphicsX = 0;
            for (int col = 0; col < viewableWidth; col++ ) {
                GridPos currentGridPos = new GridPos( topLeft.getX() + col, topLeft.getY() + row);
                g.drawImage(aCellManager.getCellImage(cells[currentGridPos.getY()][currentGridPos.getX()]), graphicsX, graphicsY, null);
                graphicsX += cellWidth;
            }
            graphicsY += cellHeight;
        }
    }

    protected GridPos determineTopLeftPos() {
        // TODO:  Figure out where the top left is
        return new GridPos(1, 1);
    }
    
    @Override
    public void onButtonPress(char button) {
        // todo implement
    }
}
