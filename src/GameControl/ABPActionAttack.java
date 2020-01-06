package GameControl;

public class ABPActionAttack implements ABPActionEffect {
    
    private final int damage;
    
    public ABPActionAttack(int damage) {
        this.damage = damage;
    }
    
    @Override
    public void performAction(ABP caster, ABP target, ABPType moveType, boolean isCrit) {
        target.takeDamage(damage, caster, moveType, isCrit);
    }
}
