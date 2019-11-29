package GameControl;

import java.awt.*;

public class AMenuItem {

    public void draw(Graphics g) {

    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     * @param g the graphics object
     * @param text the string to draw
     * @param x the center-x of the string
     * @param y the center-y of the string
     */
    public void drawCenteredString(Graphics g, String text, Font font, int x, int y) {
        // Get the FontMetrics object
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the coordinates for the text
        int cx = x - metrics.stringWidth(text)/2;
        int cy = y - metrics.getHeight() / 2 + metrics.getAscent();
        // Draw the String
        g.drawString(text, cx, cy);
    }

}
