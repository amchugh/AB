package GameControl;

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
