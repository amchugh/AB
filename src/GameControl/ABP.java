package GameControl;

public class ABP {
  
  private int health;
  private ABPSpecies species;
  
  // TODO remove default constructor. Only here for testing purposes
  public ABP() {
    species = new ABPSpecies();
  }

  public ABP(ABPSpecies species) {
    this.species = species;
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

  /**
   * This should only be used when constructing the BP.
   * @param health
   */
  public void setHealth(int health) {
    this.health = health;
  }
  
  public ABPSpecies getSpecies() {
    return species;
  }
  
}
