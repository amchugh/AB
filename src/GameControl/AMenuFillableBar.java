package GameControl;

import java.awt.*;

public class AMenuFillableBar extends AMenuItem {

    private final int x1, y1, barSize, maxLength;
    private final boolean isVertical;
    private Color barColor;

    private final double maxValue;
    private double currentValue;

    public AMenuFillableBar(int x1, int y1, int barSize, int maxLength, double maxValue, Color barColor, boolean isVertical) {
        this.x1 = x1;
        this.y1 = y1;
        this.barSize = barSize;
        this.maxLength = maxLength;
        this.maxValue = maxValue;
        this.currentValue = 0; // set this later
        this.barColor = barColor;
        this.isVertical = isVertical;
    }

    public void setValue(double newValue) {
        currentValue = newValue;
    }

    public void draw(Graphics g) {
        g.setColor(barColor);
        int w, h;
        if (!isVertical) {
            w = barSize;
            h = (int) ((currentValue/maxValue) * maxLength);
        } else {
            h = barSize;
            w = (int) ((currentValue/maxValue) * maxLength);
        }
        g.fillRect(x1, y1, h, w);
    }

}
