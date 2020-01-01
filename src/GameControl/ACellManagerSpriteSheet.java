package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ACellManagerSpriteSheet implements ACellManager {
    protected Map<Integer, Image> cells = new HashMap<>();

    protected String filename;
    protected int cellWidth, cellHeight, numWide, numHigh, eachOffsetX, eachOffsetY;
    protected int f = 0;

    public ACellManagerSpriteSheet(String filename, int cellWidth, int cellHeight, int numWide, int numHigh, int eachOffsetX, int eachOffsetY) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        Image sheet = getImage(filename);
        System.out.println("Loaded the file: " + filename);
        System.out.println("size: " + sheet.getWidth(null) + ", " + sheet.getHeight(null));
        int y = eachOffsetY;
        int id = 0;
        for (int col = 0; col < numHigh; col++) {
            int x = eachOffsetX;
            for (int row = 0; row < numWide; row++) {
                cells.put(id, getSubImage(sheet, x, y));
                // System.out.println("Copied; i: " + String.valueOf(id) + ", x: " + x + " , y: " + y);
                id = id + 1;
                f = 1;
                x += eachOffsetX + cellWidth;
            }
            y += eachOffsetY + cellHeight;
        }
    }

    private Image getSubImage(Image sheet, int x, int y) {
        Image result = new BufferedImage(cellWidth, cellHeight, TYPE_INT_RGB);
        int w = sheet.getWidth(null);
        int h = sheet.getHeight(null);
        Graphics g = result.getGraphics();
        g.drawImage(
                sheet,
                -x, -y, w, h, null);
        return result;
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
        return cellWidth;
    }

    @Override
    public int getCellHeight() {
        return cellHeight;
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
