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

  /**
   * Deals damage to the current BP
   * @param damage raw damage to take
   */
  public void takeDamage(int damage) {
    getActiveBP().takeDamage(damage);
  }

  /**
   * @return if the currently selected BP is alive
   */
  public boolean isBPAlive() {
    return getActiveBP().isAlive();
  }

  /**
   * Finds if a BP is alive
   * @return if the enemy has another BP
   */
  public boolean hasMoreBP() {
    return bpTeam.hasLivingBP();
  }

}
