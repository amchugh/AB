package Editor;

import GameControl.*;

import java.awt.*;

public class AMapEditorMain {

    private AMapDisplay display;
    private AUserInput userInput;
    private AMapEditorController control;
    
    private ACellManagerSpriteSheet cellManager;
    private AMapInstance map;

    final String CELL_RESOURCE_FILENAME = "rsc/images/map/world_map_tiles.png";

    GridPos currentPos = new GridPos(1, 1);

    public AMapEditorMain(String mapFilename) {
        cellManager = new ACellManagerSpriteSheet(CELL_RESOURCE_FILENAME,
                16, 16, 4, 50, 1, 1);
        
        try {
            map = (AMapInstance) new AMapReader(mapFilename).constructMap(cellManager);
        } catch (Exception e) {
            throw new RuntimeException("Shouldn't throw", e);
        }
        map.setHighlightDesiredCenter(true);
    
        AViewAdvisor viewAdvisor = new AViewAdvisorRectangular();
        map.setViewAdvisor(viewAdvisor);
    
        userInput = new AUserInput();

        AMapWriter mapWriter = new AMapWriter(mapFilename);

        control = new AMapEditorController(userInput);
        display = new AMapDisplay(new Dimension(1200, 900), cellManager, map, mapWriter);
        display.addKeyListener(userInput);
    }

    public void step() {
        control.step();
        handleMoveCurrentGridPos();
        handleMapModifications();
        map.setViewFocus(currentPos);
        map.step();
    }

    protected void handleMapModifications() {
        if (control.getPlaceCell()) {
            map.setCell(currentPos.getY(), currentPos.getX(), display.getCurrentCellId());
            display.repaint();
        }
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
                display.repaint();
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
