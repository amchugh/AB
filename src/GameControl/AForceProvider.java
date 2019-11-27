package GameControl;

/**
 * An implementation of the AForceProvider can be consulted to see if a
 * force is being applied in a specific direction.  Further a force can
 * be assigned a relative magnitude.
 */
public interface AForceProvider {
    public enum ForceDirection {UP, DOWN, LEFT, RIGHT};

    boolean isForcePresent(ForceDirection d);

    int forceMagnitude(ForceDirection d);
}
