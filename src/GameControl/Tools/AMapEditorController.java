package GameControl.Tools;

import GameControl.AUserInput;

public class AMapEditorController {

    private AUserInput userInput;
    private static final double repeatTime = 1e3 / 10;

    public enum MovementDirection {NONE, UP, DOWN, LEFT, RIGHT}

    ;

    private MovementDirection movementDirection;

    public AMapEditorController(AUserInput userInput) {
        this.userInput = userInput;
        movementDirection = MovementDirection.NONE;
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
}
