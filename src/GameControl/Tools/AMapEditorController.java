package GameControl.Tools;

import GameControl.AMap;
import GameControl.AMapInstance;
import GameControl.AUserInput;

import javax.swing.*;
import java.awt.*;

public class AMapEditorController extends JPanel {

    private AUserInput userInput;
    private static final double repeatTime = 1e3 / 10;
    public AMapInstance map;
    
    public enum MovementDirection {NONE, UP, DOWN, LEFT, RIGHT}

    ;

    private MovementDirection movementDirection;

    public AMapEditorController(AUserInput userInput, AMapInstance map) {
        this.userInput = userInput;
        movementDirection = MovementDirection.NONE;
        this.map = map;
    }

    public MovementDirection getMovementDirection() {
        MovementDirection result = this.movementDirection;
        this.movementDirection = MovementDirection.NONE;
        return result;
    }

    public void step() {
        if (isKeyReady('w')) {
            movementDirection = MovementDirection.UP;
        } else if (isKeyReady('s')) {
            movementDirection = MovementDirection.DOWN;
        } else if (isKeyReady('a')) {
            movementDirection = MovementDirection.LEFT;
        } else if (isKeyReady('d')) {
            movementDirection = MovementDirection.RIGHT;
        }
        if (isKeyReady(' ')) {
            // Place current tile
        }
    }

    private boolean isKeyReady(char key) {
        return userInput.isKeyPressed(key) && userInput.isKeyTimedIn(key, repeatTime);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gg = (Graphics2D) g;
        
        // Draw a border on the canvas
        gg.setColor(Color.BLACK);
        gg.drawRect(0, 0, getWidth()-1, getHeight()-1);
        
        // Draw the map
        map.draw(g);
    }
    
}
