package GameControl;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

public class AMenuTextItem extends AMenuItem {

    private Font font;
    private Color color;
    private int x, y;
    private String text;

    private final boolean doDrawCentered;
    private final boolean doDrawFromTop;
    private final boolean doDrawFromLeft;

    public AMenuTextItem(String text, int x, int y, Color color, Font font) {
        this(text, x, y, color, font, false);
    }

    public AMenuTextItem(String text, int x, int y, Color color, Font font, boolean doDrawCentered) {
        this(text, x, y, color, font, doDrawCentered, false, false);
    }
    
    public AMenuTextItem(String text, int x, int y, Color color, Font font, boolean doDrawCentered, boolean doDrawFromTop, boolean doDrawFromLeft) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
        this.doDrawCentered = doDrawCentered;
        this.doDrawFromTop = doDrawFromTop;
        this.doDrawFromLeft = doDrawFromLeft;
    }
    
    public void updateText(String newText) {
        text = newText;
    }

    @Override
    public void draw(Graphics g) {
        int tempy = y;
        int tempx = x;
        Rectangle bounds = getStringBounds((Graphics2D) g, text, tempx, tempy);
        if (doDrawFromTop) {
            tempy += bounds.getHeight();
        }
        if (doDrawFromLeft) {
            tempx += bounds.getWidth();
        }
        if (doDrawCentered) {
            // Draw the text at the center
            g.setFont(font);
            g.setColor(color);
            drawCenteredString(g, text, font, tempx, tempy);
        } else {
            // Draw the text at the bottom left
            g.setFont(font);
            g.setColor(color);
            g.drawString(text, tempx, tempy);
        }
    }
    
    private Rectangle getStringBounds(Graphics2D g, String str, int x, int y) {
        if (text == null) {
            throw new IllegalArgumentException("String must be defined");
        }
        FontRenderContext frc = g.getFontRenderContext();
        GlyphVector gv = g.getFont().createGlyphVector(frc, str);
        Rectangle r = gv.getPixelBounds(null, x, y);
        return r;
    }

}
