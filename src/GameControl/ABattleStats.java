package GameControl;

public class ABattleStats extends AStats {
    
    public double armorVulnerability;
    
    private static final double armorDamageMultiplier = 0.04d;
    
    public ABattleStats(AStats bpStats, AStats speciesStats) {
        this.hitpoints = bpStats.hitpoints * speciesStats.hitpoints;
        this.armorStrength = bpStats.armorStrength * speciesStats.armorStrength;
        this.armorDurability = bpStats.armorDurability * speciesStats.armorDurability;
        this.attackPower = bpStats.attackPower * speciesStats.attackPower;
        this.attackPierce = bpStats.attackPierce * speciesStats.attackPierce;
        this.speed = bpStats.speed * speciesStats.speed;
        this.endurance = bpStats.endurance * speciesStats.endurance;
        this.armorVulnerability = 0;
    }
    
    public void takeArmorDamage(ABattleStats opponent) {
        double increase = armorDamageMultiplier * armorDurability * opponent.attackPierce;
        armorVulnerability += increase;
    }
    
}
