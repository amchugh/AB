package GameControl;

public class ABPActionStatEffect implements ABPActionEffect {
    
    private boolean doesEffectCaster;
    private AStats.STATS statToEffect;
    private double modifier;
    
    public ABPActionStatEffect(boolean doesEffectCaster, AStats.STATS statToEffect, double strength) {
        this.doesEffectCaster = doesEffectCaster;
        this.statToEffect = statToEffect;
        this.modifier = strength;
    }
    
    @Override
    public void performAction(ABP caster, ABP target, ABPType moveType, boolean isCrit) {
        // Get the battle stat to effect
        ABattleStats toEffect;
        if (doesEffectCaster) {
            toEffect = caster.getBattleStats();
        } else {
            toEffect = target.getBattleStats();
        }
        // Modify the appropriate stat
        switch (statToEffect) {
            case HP -> {
                toEffect.hitpoints *= modifier;
            }
            case AS -> {
                toEffect.armorStrength *= modifier;
            }
            case AD -> {
                toEffect.armorDurability *= modifier;
            }
            case AP -> {
                toEffect.attackPower *= modifier;
            }
            case APP -> {
                toEffect.attackPierce *= modifier;
            }
            case SP -> {
                toEffect.speed *= modifier;
            }
            case ED -> {
                toEffect.endurance *= modifier;
            }
        }
    }
}
