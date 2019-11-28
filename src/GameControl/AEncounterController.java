package GameControl;

public class AEncounterController implements AMenuFlowProvider{

    private AUserInput userInput;

    public static final double repeatTime = 1e3 / 10 * 5;

    public AEncounterController(AUserInput userInput) {
        this.userInput = userInput;
    }

    @Override
    public FlowDirection currentDirectionForce() {
        FlowDirection best = null;
        double t = -1;
        for (FlowDirection d : FlowDirection.values()) {
            double testTime = userInput.getKeyPressTime(getCharFromDirection(d));
            if (testTime > t) {
                if (userInput.isKeyTimedIn(getCharFromDirection(d), repeatTime)) {
                    t = testTime;
                    best = d;
                }
            }
        }
        return best;
    }

    private char getCharFromDirection(FlowDirection d) {
        return switch (d) {
            case UP -> 'w';
            case DOWN -> 's';
            case LEFT -> 'a';
            case RIGHT -> 'd';
        };
    }

    @Override
    public boolean doSelect() {
        return userInput.isKeyPressed('e');
    }

    @Override
    public boolean doCancel() {
        return userInput.isKeyPressed('q');
    }
}
