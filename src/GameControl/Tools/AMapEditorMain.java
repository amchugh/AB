package GameControl.Tools;

import GameControl.*;

import java.awt.*;

public class AMapEditorMain {

    private AMapDisplay display;
    private AUserInput userInput;
    private AMapEditorController control;
    
    private ACellManagerSpriteSheet cellManager;

    GridPos currentPos = new GridPos(1, 1);

    public AMapEditorMain(String mapFilename) {
        cellManager = new ACellManagerSpriteSheet("rsc/images/map/world_map_tiles.png",
                16, 16, 4, 50, 1, 1);
        
        AMapInstance map;
        try {
            map = (AMapInstance) new AMapReader(mapFilename).constructMap(cellManager);
        } catch (Exception e) {
            throw new RuntimeException("Shouldn't throw", e);
        }
    
        AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
        map.setViewAdvisor(viewAdvisor);
    
        userInput = new AUserInput();
    
        control = new AMapEditorController(userInput, map);
        display = new AMapDisplay(new Dimension(800, 600), cellManager, map);
        display.addKeyListener(userInput);
    }

    public void step() {
        control.step();
        handleMoveCurrentGridPos();
        control.map.setViewFocus(currentPos);
        control.map.getScene().step();
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
            if (control.map.isGridPosOccupiable(desiredPos)) {
                currentPos = desiredPos;
                System.out.println("Setting currentPos to be; " + currentPos.getX() + ", " + currentPos.getY());
            }
        }
    }

    public void loop() {
        // make the display visible
        display.setVisible(true);

        while (true) {
            step();
        }
    }
}
