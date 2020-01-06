package GameControl;

/**
 * ABPAction is a "Move" that a BP can learn and then use in an Encounter.
 * In the future, this class will have more information and will not just have a damage field
 * Some instances, for example, might heal the BP that uses it or raise a specific stat
 * For the basic scenario however, this class just contains the damage that moves will do
 */
public class ABPAction extends AIDItem {

    private ABPActionEffect[] effects;
    private final String name;
    private final ABPType type;

    public ABPAction(int id, ABPType type, String name, ABPActionEffect[] effects) {
        super (id);
        this.type = type;
        this.name = name;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }
    public ABPType getType() {
        return type;
    }
    
    /**
     * Performs this ABPAction
     * @param caster the ABP that "used" this action
     * @param enemy the ABP that has been targeted by the action
     * @param isCrit should the effects of this action be increased?
     */
    public void performActions(ABP caster, ABP enemy, boolean isCrit) {
        for (int i = 0; i < effects.length; i++) {
            effects[i].performAction(caster, enemy, type, isCrit);
        }
    }
    
}
