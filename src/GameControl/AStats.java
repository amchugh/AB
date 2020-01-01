package GameControl;

public class AStats {
    
    public int hitpoints, armorStrength, armorDurability, attackPower, attackPierce, speed, endurance;
    
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
    
}
