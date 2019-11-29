package GameControl;

import java.util.ArrayList;

public class ABP {
  
  private int health;
  private ABPSpecies species;
  private ArrayList<ABPAction> actions;

  public static final int MAX_ACTIONS = 4;

  public ABP(ABPSpecies species) {
    this.species = species;
    actions = new ArrayList<>();
  }

  public ArrayList<ABPAction> getActions() {
    if (actions.size() == 0) {
      throw new RuntimeException("Moves have not been initialized on this BP yet");
    }
    return actions;
  }

  public void addAction(ABPAction a) {
    if (actions.size() == MAX_ACTIONS) {
      throw new IllegalArgumentException("BP already has 4 registered moves");
    }
    actions.add(a);
  }

  /**
   * Causes the BP to take damage
   * @param damage the raw amount to take
   */
  public void takeDamage(int damage) {
    health -= damage;
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

  public boolean isAlive() {
    if (health <= 0) {
      return false;
    }
    return true;
  }

  public int getHealth() { return health; }
  public int getMaxHealth() { return getSpecies().getMaxHealth(); }

  public String getName() {
    // todo:: add custom names
    return getSpecies().getName();
  }

}
