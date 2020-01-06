package GameControl;

/**
 * This interface details any type of effect that an Action can take. An array of these ActionEffects is stored with
 * every ABP action, and each of these effects are called in order when the Action is "used".
 *
 * //improveme:: perhaps in the future this is the place where animation details are stored?
 */
public interface ABPActionEffect {
    
    void performAction(ABP caster, ABP target, ABPType moveType, boolean isCrit);
    
}
