package GameControl;

import java.awt.*;

public class AMenuSelectable {

    private String text;
    private Color backgroundColor, textColor;
    private int x1, x2, y1, y2;

    private AMenuSelectable left;
    private AMenuSelectable right;
    private AMenuSelectable up;
    private AMenuSelectable down;

    private static final Font font = new Font("Serif", Font.PLAIN, 24);

    public AMenuSelectable(String text, int x1, int y1, int x2, int y2, Color backgroundColor, Color textColor) {
        this.text = text;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public void setBackgroundColor(Color n) {
        backgroundColor = n;
    }

    public AMenuSelectable getLeft() {
        return left;
    }

    public void setLeft(AMenuSelectable left) {
        this.left = left;
    }

    public AMenuSelectable getRight() {
        return right;
    }

    public void setRight(AMenuSelectable right) {
        this.right = right;
    }

    public AMenuSelectable getUp() {
        return up;
    }

    public void setUp(AMenuSelectable up) {
        this.up = up;
    }

    public AMenuSelectable getDown() {
        return down;
    }

    public void setDown(AMenuSelectable down) {
        this.down = down;
    }

    public void draw(Graphics g) {
        // Draw the rectangle first
        g.setColor(backgroundColor);
        g.fillRect(x1, y1, x2-x1, y2-y1);
        // Draw the text in the rectangle
        g.setFont(font);
        g.setColor(textColor);
        FontMetrics fontMetrics = g.getFontMetrics();
        drawCenteredString(g, text, font, x1 + (x2-x1)/2, y1 + (y2-y1)/2);
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
