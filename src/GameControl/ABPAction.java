package GameControl;

/**
 * ABPAction is a "Move" that a BP can learn and then use in an Encounter.
 * In the future, this class will have more information and will not just have a damage field
 * Some instances, for example, might heal the BP that uses it or raise a specific stat
 * For the basic scenario however, this class just contains the damage that moves will do
 */
public class ABPAction extends AIDItem {

    private final int damage;

    public ABPAction(int id, int damage) {
        super (id);
        this.damage = damage;
    }

}
