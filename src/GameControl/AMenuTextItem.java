package GameControl;

import java.awt.*;

public class AMenuTextItem extends AMenuItem {

    private Font font;
    private Color color;
    private int x, y;
    private String text;

    private final boolean doDrawCentered;

    public AMenuTextItem(String text, int x, int y, Color color, Font font) {
        this(text, x, y, color, font, false);
    }

    public AMenuTextItem(String text, int x, int y, Color color, Font font, boolean doDrawCentered) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
        this.doDrawCentered = doDrawCentered;
    }

    @Override
    public void draw(Graphics g) {
        if (doDrawCentered) {
            // Draw the text at the center
            g.setFont(font);
            g.setColor(color);
            drawCenteredString(g, text, font, x, y);
        } else {
            // Draw the text at the bottom left
            g.setFont(font);
            g.setColor(color);
            g.drawString(text, x, y);
        }
    }

}
