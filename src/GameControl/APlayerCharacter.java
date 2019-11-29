package GameControl;

public class APlayerCharacter {
  
  // TODO remove this field too and replace with actual code.
  private ABPTeam bpTeam;

  // TODO remove default constructor. Only here for testing purposes
  public APlayerCharacter() {
    bpTeam = new ABPTeam();
  }

  public void addBP(ABP bp) {
    bpTeam.addBP(bp);
  }

  // TODO this method will stay, but needs to be edited with the rest of the class
  public ABP getActiveBP() {
    return bpTeam.getSelected();
  }
}
