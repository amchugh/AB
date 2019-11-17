package GameControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class ACellManagerSimple implements ACellManager {
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
            return ImageIO.read(new File("rsc/static16x16.png"));
        } catch (Exception e) {
            throw new RuntimeException("Simple implementation must always work", e);
        }
    }
}
