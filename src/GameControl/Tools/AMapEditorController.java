package GameControl.Tools;

import GameControl.AUserInput;

/**
 * An AMapEditorController is here only to interpret the keyboard input seen by
 * the application and translate the inputs into application actions.
 */
public class AMapEditorController {

    private AUserInput userInput;
    private static final double repeatTime = 1e3 / 10;

    public enum MovementDirection {NONE, UP, DOWN, LEFT, RIGHT}
    private MovementDirection movementDirection;
    private boolean placeCell;

    public AMapEditorController(AUserInput userInput) {
        this.userInput = userInput;
        movementDirection = MovementDirection.NONE;
        placeCell = false;
    }

    public MovementDirection getMovementDirection() {
        MovementDirection result = this.movementDirection;
        this.movementDirection = MovementDirection.NONE;
        return result;
    }

    public boolean getPlaceCell() {
        boolean result = placeCell;
        placeCell = false;
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
            placeCell = true;
        }
    }

    private boolean isKeyReady(char key) {
        return userInput.isKeyPressed(key) && userInput.isKeyTimedIn(key, repeatTime);
    }
}
