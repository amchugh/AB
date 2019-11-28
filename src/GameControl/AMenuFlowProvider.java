package GameControl;

public interface AMenuFlowProvider {

    public enum FlowDirection {UP, DOWN, LEFT, RIGHT};

    FlowDirection currentDirectionForce();
    boolean doSelect();
    boolean doCancel();

}
