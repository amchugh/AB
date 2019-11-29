package GameControl;

import java.awt.*;

public class AMenuNarrationItem extends AMenuItem {

    private String text;
    private Color backgroundColor, textColor;
    private int x1, x2, y1, y2;

    private static final Font font = new Font("Serif", Font.PLAIN, 24);

    public AMenuNarrationItem(String text, int x1, int y1, int x2, int y2, Color backgroundColor, Color textColor) {
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

    @Override
    public void draw(Graphics g) {
        // Draw the rectangle first
        g.setColor(backgroundColor);
        g.fillRect(x1, y1, x2-x1, y2-y1);
        // Draw the text in the rectangle
        g.setFont(font);
        g.setColor(textColor);
        drawCenteredString(g, text, font, x1 + (x2-x1)/2, y1 + (y2-y1)/2);
    }

}
