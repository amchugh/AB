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
  
  // Stats
  private AStats bpPersonalStats;
  // Effective stats
  private AStats effectiveStats;
  private ABattleStats battleStats;
  
  // XP
  private int xpPoints;
  private int level;
  
  public ABP(ABPSpecies species, AStats bpPersonalStats, int xpPoints) {
    this.species = species;
    this.bpPersonalStats = bpPersonalStats;
    this.xpPoints = xpPoints;
    level = getLevelFromXP(xpPoints);
    // Set the health to zero to start
    health = 0;
    actions = new ArrayList<>();
  }

  public void onBattleStart() {
    AStats es = getEffectiveStats();
    // Setup the battle stats
    battleStats = new ABattleStats(es.armorStrength, es.speed);
  }
  
  public ArrayList<ABPAction> getActions() {
    if (actions.size() == 0) {
      // TODO:: REMOVE ME!!!!
      //throw new RuntimeException("Moves have not been initialized on this BP yet");
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
  public void takeDamage(int damage, ABP attacker, ABPType damageType, boolean isCrit) {
    //Damage Taken = (Attack Power / Defense) * Move Strength (damage) * Effective * Crit
    int damageTaken = (attacker.getEffectiveStats().attackPower / battleStats.defense) * damage;
    if (isBPWeak(damageType)) {
      damageTaken *= TYPE_WEAKNESS_MULTIPLIER;
    }
    if (isCrit) {
      damageTaken *= CRIT_MULTIPLIER;
    }
    health -= damageTaken;
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
    if (customName == null || customName.equals(""))
      return getSpecies().getName();
    return customName;
  }

  public boolean hasCustomName() {
    if (customName == null || customName.equals("")) {
      return false;
    }
    return true;
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

  private AStats getEffectiveStats() {
    if (effectiveStats == null) {
      AStats s = new AStats(bpPersonalStats);
      s.add(getSpecies().getStats());
      s.multiply(level);
      effectiveStats = s;
    }
    return effectiveStats;
  }
  
  private int getLevelFromXP(int xp) {
    int l = 0;
    while (getTotalXPNeededForLevel(l) < xp) {
      l++;
    }
    return l;
  }
  
  private int getXPNeededForLevel(int level) {
    return (int)Math.floor(10 * Math.pow(level, 0.8d));
  }
  
  private int getTotalXPNeededForLevel(int level) {
    int t = level;
    int total = 0;
    while (t > 0) {
      total += getXPNeededForLevel(t);
      t--;
    }
    return total;
  }
  
  public ABattleStats getBattleStats() {
    if (battleStats == null) {
      throw new RuntimeException("Battle stats were not properly set up!");
    }
    return battleStats;
  }
  
  public int getXP() {
    return xpPoints;
  }
  
  public AStats getPersonalStats() {
    return bpPersonalStats;
  }
  
}
