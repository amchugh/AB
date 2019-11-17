package GameControl;

import java.awt.*;

public class AMapInstance implements AMap {
    private int id;
    private int width;
    private int height;
    private int [][] cells;

    AMapInstance( int _id, int _width, int _height) {
        id = _id;
        width = _width;
        height = _height;

        cells = new int[height][width];
    }

    public void setCell(int row, int col, int cell) {
        // TODO:  Confirm the validity of the cell identifier
        assert(row < height);
        assert(col < width);

        cells[row][col] = cell;
    }

    // AMap methods
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }

}
