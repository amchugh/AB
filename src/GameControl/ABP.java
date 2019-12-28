package GameControl;

import java.util.ArrayList;

public class ABP {
  
  private int health;
  private ABPSpecies species;
  private ArrayList<ABPAction> actions;
  private String customName;

  // The maximum number of actions any one BP is allowed to know
  public static final int MAX_ACTIONS = 4;
  // The % increased damage to take if weak against a type
  private static final float TYPE_WEAKNESS_MULTIPLIER = 1.5f;
  // The % increased damage to take to a critical hit
  private static final float CRIT_MULTIPLIER = 1.5f;
  
  public ABP(ABPSpecies species) {
    this.species = species;
    // Set the health to zero to start
    health = 0;
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
      throw new IllegalArgumentException("BP already has 4 registered actions");
    }
    actions.add(a);
  }

  /**
   * Causes the BP to take damage
   * @param damage the raw amount to take. This is before the actual amount is calculated by this method
   * @param damageType the Type of move that inflicted this damage. Used for type weaknesses
   */
  public void takeDamage(int damage, ABPType damageType, boolean isCrit) {
    if (isBPWeak(damageType)) {
      damage *= TYPE_WEAKNESS_MULTIPLIER;
    }
    if (isCrit) {
      damage *= CRIT_MULTIPLIER;
    }
    health -= damage;
  }

  /**
   * This should probably only be used when constructing the BP.
   * @param health
   */
  public void setHealth(int health) { this.health = health; }
  
  public ABPSpecies getSpecies() { return species; }

  public boolean isAlive() {
    return health > 0;
  }

  public int getHealth() { return health; }
  public int getMaxHealth() { return getSpecies().getMaxHealth(); }

  public String getName() {
    if (customName == null)
      return getSpecies().getName();
    return customName;
  }

  public void setCustomName(String n) {
    customName = n;
  }
  
  public boolean isBPWeak(ABPType type) {
    return species.getBpType().isWeakAgainst(type);
  }
  
  public boolean isBPImmune(ABPType type) {
    return species.getBpType().isImmuneAgainst(type);
  }

}
