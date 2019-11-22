package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ACellManagerSimple implements ACellManager {

    private Map<Integer, Image> cells = new HashMap<>();

    ACellManagerSimple() {
        cells.put(0, getImage("rsc/images/map/grass16x16.png"));
        cells.put(1, getImage("rsc/images/map/sand16x16.png"));
        cells.put(2, getImage("rsc/images/map/water16x16.png"));
    }

    private Image getImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (Exception e) {
            throw new RuntimeException("Simple implementation must always work", e);
        }
    }
    @Override
    public int getCellWidth() {
        return 16;
    }
    @Override
    public int getCellHeight() {
        return 16;
    }

    @Override
    public Image getCellImage(int cellId) {
        try {
            return cells.get(cellId);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected cellId: " + cellId, e);
        }
    }

    @Override
    public boolean isCellIdValid(int cellId) {
        return cells.containsKey(cellId);
    }
}
