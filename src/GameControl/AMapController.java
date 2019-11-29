package GameControl;

public class AMapController implements AForceProvider {

    private AUserInput userInput;
    private static final double repeatTime = 1e3 / 10;

    public AMapController(AUserInput userInput) {
        this.userInput = userInput;
    }

    @Override
    public boolean isForcePresent(AForceProvider.ForceDirection d) {
        return switch (d) {
            case UP -> isKeyReady('w');
            case DOWN -> isKeyReady('s') ;
            case LEFT -> isKeyReady('a');
            case RIGHT -> isKeyReady('d');
        };
    }

    private boolean isKeyReady(char key) {
        return userInput.isKeyPressed(key) && userInput.isKeyTimedIn(key, repeatTime);
    }

    @Override
    public int forceMagnitude(AForceProvider.ForceDirection d) {
        if (isForcePresent(d)) {
            return 1;
        } else {
            return 0;
        }
    }
}
