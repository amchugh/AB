package GameControl;

public class APlayerCharacter {
  
  // TODO remove this field too and replace with actual code.
  private ABPTeam bpTeam;

  // The player has a current grid position on the map.
  private GridPos currentPos;
  private AForceProvider movementProvider;
  private AGridPosValidator gridPosValidator;
  
  // TODO remove default constructor. Only here for testing purposes
  public APlayerCharacter() {
    bpTeam = new ABPTeam();
    currentPos = new GridPos(1, 1);
  }

  public void addBP(ABP bp) {
    bpTeam.addBP(bp);
  }

  public GridPos getGridPos() {
    return currentPos;
  }
  
  // TODO this method will stay, but needs to be edited with the rest of the class
  public ABP getActiveBP() {
    return bpTeam.getSelected();
  }

  public void setDesiredMovementProvider(AForceProvider forceProvider) {
    movementProvider = forceProvider;
  }

  public void setGridPosValidator(AGridPosValidator gridPosValidator) {
    this.gridPosValidator = gridPosValidator;
  }

  public void step() {
    // TODO:  Current the player can only move but this is dependent on the specific scene so
    // it is likely that we will want to factor this out of this class so that the Player can
    // exist as a construct independent of whether they are currently in a battle or on the map.
    handleMove();
  }

  private void handleMove() {
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
