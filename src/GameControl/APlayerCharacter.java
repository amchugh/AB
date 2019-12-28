package GameControl;

public class APlayerCharacter {
  
  private ABPTeam bpTeam;

  // todo:: remove default constructor. Only here for testing purposes
  // todo:: this should be loaded in. Perhaps the constructor will change.
  public APlayerCharacter() {
    bpTeam = new ABPTeam();
  }

  public void addBP(ABP bp) {
    bpTeam.addBP(bp);
  }

  public ABPTeam getTeam() {
    return bpTeam;
  }

  public ABP getActiveBP() {
    return bpTeam.getSelected();
  }
  
  public void onBattleStart() {
    bpTeam.onBattleStart();
  }
  
}
