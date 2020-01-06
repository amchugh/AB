package GameControl;

public class AStats {
    
    public double hitpoints, armorStrength, armorDurability, attackPower, attackPierce, speed, endurance;
    
    public enum STATS { HP, AS, AD, AP, APP, SP, ED };
    
    public AStats() {
    
    }
    
    public AStats(AStats o) {
        hitpoints = o.hitpoints;
        armorStrength = o.armorStrength;
        armorDurability = o.armorDurability;
        attackPower = o.attackPower;
        attackPierce = o.attackPierce;
        speed = o.speed;
        endurance = o.endurance;
    }
    
    /*
    public static AStats getEffectiveStats(AStats bpStats, AStats speciesStats, int level) {
        AStats r = new AStats();
        r.hitpoints = bpStats.hitpoints * speciesStats.hitpoints;
        r.armorStrength = bpStats.armorStrength * speciesStats.armorStrength;
        r.armorDurability = bpStats.armorDurability * speciesStats.armorDurability;
        r.attackPower = bpStats.attackPower * speciesStats.attackPower;
        r.attackPierce = bpStats.attackPierce * speciesStats.attackPierce;
        r.speed = bpStats.speed * speciesStats.speed;
        r.endurance = bpStats.endurance * speciesStats.endurance;
        return r;
    }
    
    public void add(AStats o) {
        hitpoints += o.hitpoints;
        armorStrength += o.armorStrength;
        armorDurability += o.armorDurability;
        attackPower += o.attackPower;
        attackPierce += o.attackPierce;
        speed += o.speed;
        endurance += o.endurance;
    }
    
    public void multiply(float scalar) {
        hitpoints *= scalar;
        armorStrength *= scalar;
        armorDurability *= scalar;
        attackPierce *= scalar;
        attackPower *= scalar;
        speed *= scalar;
        endurance *= scalar;
    }
    */
    
}
