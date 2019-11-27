package GameControl;

public class AEnemy {

  private ABPTeam bpTeam;
  
  // TODO remove default constructor. Only here for testing purposes.
  // TODO this whole class needs to be expanded on
  public AEnemy() {
    bpTeam = new ABPTeam();
  }

  public AEnemy(ABP bp) {
    bpTeam = new ABPTeam();
    bpTeam.addBP(bp);
  }

  public void addBP(ABP n) {
    bpTeam.addBP(n);
  }

  public ABP getActiveBP() {
    return bpTeam.getSelected();
  }
  
}
