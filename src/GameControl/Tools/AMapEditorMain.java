package GameControl.Tools;

import GameControl.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class AMapEditorMain {

    private AMapDisplay display;
    private AUserInput userInput;
    private AMapEditorController control;
    private AMapInstance map;
    private ACellManagerSpriteSheet cellManager;

    GridPos currentPos = new GridPos(1, 1);

    public AMapEditorMain(String mapFilename) {
        cellManager = new ACellManagerSpriteSheet("rsc/images/map/world_map_tiles.png",
                16, 16, 4, 50, 1, 1);

        display = new AMapDisplay(new Dimension(800, 600), cellManager);
        userInput = new AUserInput();
        control = new AMapEditorController(userInput);

        display.addKeyListener(userInput);

        try {
            map = (AMapInstance) new AMapReader(mapFilename).constructMap(cellManager);
        } catch (Exception e) {
            throw new RuntimeException("Shouldn't throw", e);
        }

        AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
        map.setViewAdvisor(viewAdvisor);
    }

    public void step() {
        control.step();
        handleMoveCurrentGridPos();
        map.setViewFocus(currentPos);
        map.getScene().step();
    }


    protected void handleMoveCurrentGridPos() {
        GridPos desiredPos = switch (control.getMovementDirection()) {
            case UP -> new GridPos(currentPos.getX(), currentPos.getY() - 1);
            case DOWN -> new GridPos(currentPos.getX(), currentPos.getY() + 1);
            case LEFT -> new GridPos(currentPos.getX() - 1, currentPos.getY());
            case RIGHT -> new GridPos(currentPos.getX() + 1, currentPos.getY());
            case NONE -> currentPos;
        };
        if (!(desiredPos.equals(currentPos))) {
            if (map.isGridPosOccupiable(desiredPos)) {
                currentPos = desiredPos;
                System.out.println("Setting currentPos to be; " + currentPos.getX() + ", " + currentPos.getY());
            }
        }
    }

    public void draw(Graphics g) {
        map.getScene().draw(g);
    }

    public void loop() {
        // make the display visible
        display.setVisible(true);

        while (true) {
            step();

            // Jason :: TODO:  Figure out why the buttons are not showing the click animation nor responding.

            BufferStrategy b = display.canvas.getBufferStrategy();
            Graphics g = b.getDrawGraphics();

            // Allow the window to draw first
            display.update(g);
            draw(g);

            g.dispose();
            b.show();
        }
    }
}
