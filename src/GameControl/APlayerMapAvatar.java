package GameControl;

public class APlayerMapAvatar {
    private APlayerCharacter player;
    private GridPos currentPos;
    private AForceProvider movementProvider;
    private AGridPosValidator gridPosValidator;

    public APlayerMapAvatar(APlayerCharacter player, AForceProvider forceProvider) {
        this.player = player;
        this.movementProvider = forceProvider;
        currentPos = new GridPos(1, 1);
    }

    public void setGridPosValidator(AGridPosValidator gridPosValidator) {
        this.gridPosValidator = gridPosValidator;
    }

    public GridPos getGridPos() {
        return currentPos;
    }

    public void step() {
    }

    public void handleMove() {
        GridPos desiredPos = new GridPos(currentPos.getX(), currentPos.getY());
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.UP)) {
            desiredPos.setY(desiredPos.getY() - 1);
        }
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.DOWN)) {
            desiredPos.setY(desiredPos.getY() + 1);
        }
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.LEFT)) {
            desiredPos.setX(desiredPos.getX() - 1);
        }
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.RIGHT)) {
            desiredPos.setX(desiredPos.getX() + 1);
        }
        if (!(desiredPos.equals(currentPos))) {
            // System.out.println("Evaluating moved position; newPos: " + desiredPos.getX() + ", " + desiredPos.getY());
            if (gridPosValidator.isGridPosOccupiable(desiredPos)) {
                currentPos = desiredPos;
                System.out.println("Setting currentPos to be; " + currentPos.getX() + ", " + currentPos.getY());
            }
        }
    }
}
