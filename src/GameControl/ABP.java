package GameControl;

public class ABP {
  
  private int health;
  private ABPSpecies species;
  
  // TODO remove default constructor. Only here for testing purposes
  public ABP() {
    species = new ABPSpecies();
  }
  
  /**
   * Causes the BP to take damage
   * @param damage the raw amount to take
   * @return whether the BP is still alive
   */
  public boolean takeDamage(int damage) {
    health -= damage;
    if (health < 0) {
      return false;
    }
    return true;
  }
  
  public ABPSpecies getSpecies() {
    return species;
  }
  
}
