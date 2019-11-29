package GameControl;

import java.util.ArrayList;

public class AEnemy {

  private ABPTeam bpTeam;
  private final String deathText;

  public AEnemy(String deathText) {
    this.deathText = deathText;
    bpTeam = new ABPTeam();
  }

  public AEnemy(ABP bp, String deathText) {
    bpTeam = new ABPTeam();
    bpTeam.addBP(bp);
    this.deathText = deathText;
  }

  //todo:: add other constructors

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

  public String getDeathText() {
    return deathText;
  }

  public ABPAction getMove() {
    // todo:: expand on this. This is the main "AI" of the enemy.
    // For now, get a random move
    ArrayList<ABPAction> actions = getActiveBP().getActions();
    return actions.get(AGameMain.random.nextInt(actions.size()));
  }

}
