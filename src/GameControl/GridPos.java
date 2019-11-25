package GameControl;

/**
 * Encapsulation of a position within a grid.  An instance of GridPos defines an x and y coordinate
 * that is relative to a collection of cells arranged in the normal two dimensional fashion.
 */
public class GridPos {
    private int x;
    private int y;

    GridPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;

        GridPos other = (GridPos) obj;
        return other.getX() == getX() && other.getY() == getY();
    }

    @Override
    public int hashCode() {
        return x + 31*y;
    }
}
