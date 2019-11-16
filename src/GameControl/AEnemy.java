package GameControl;

public class AEnemy {
  
  private ABP bp;
  
  // TODO remove default constructor. Only here for testing purposes.
  // TODO this whole class needs to be expanded on
  public AEnemy() {
    bp = new ABP();
  }
  
  public ABP getActiveBP() {
    return bp;
  }
  
}
