package GameControl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

// fixme:: feel free to change this if you have a better idea on how to handle
public class AUserInput extends KeyAdapter implements KeyListener {
    private class KeyPress {
        final char c;
        final double buttonDownTime;
        double lastConsumeTime;
        boolean hasBeenConsumed;

        KeyPress(char c, double t) {
            this.c = c;
            this.buttonDownTime = t;
            hasBeenConsumed = false;
        }
    }

    private Collection<KeyPress> pressedKeys;

    public AUserInput() {
        // A synchronized collection is required here as there are multiple threads which
        // will be accessing the pressedKeys.  This is because the KeyListener interface
        // implemented here is exercised by the UI thread which is created by the window
        // and handles receiving and processing UI inputs.  This thread is separate from
        // the main thread that is automatically created in 'main' and which runs the main
        // game logic.  Without a synchronized data container then things go very poorly.
        // todo:: this doesn't work as intended at the current time.
        // todo:: the issue likely occurs during the stream portion of isKeyPressed
        // todo:: as it stands, the ConcurrentModificationException only occurs if
        // todo:: the pressedKeys collection grows large enough.
        // todo:: This is why removing the KeyPress after using it in isKeyPressed seemed to solve the issue.
        // todo:: currently, we get around the issue by only adding the key to the pressedKeys collection if
        // todo:: a key press with the same character does not already exist.
        // todo:: we will likely want to keep this feature, but it does not solve the error. It only reduces the
        // todo:: probability of the two threads colliding significantly. This error can still occur, but it is
        // todo:: unlikely currently. The threading should still be fixed.
        pressedKeys = Collections.synchronizedCollection(new ArrayList<KeyPress>());
    }

    public void keyPressed(KeyEvent e) {
        if (isKeyPressed(e.getKeyChar()))
            return;
        pressedKeys.add(new KeyPress(e.getKeyChar(), System.currentTimeMillis()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.removeIf(s -> s.c == e.getKeyChar());
    }

    public boolean isKeyPressed(char c) {
        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        boolean finalResult = result != null && result.count() > 0;
        return finalResult;
    }

    /**
     * Returns if this is the first time the key has been consumed
     * @param c
     * @return
     */
    public boolean isKeyPressedFirstTime(char c) {
        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        if (result == null)
            return false;
        KeyPress k = result.findFirst().get();
        if (k.hasBeenConsumed)
            return false;
        k.hasBeenConsumed = true;
        return true;
    }

    /**
     * Gets the time of the key press
     * @param c the key to find
     * @return the time in millis when the key was pressed (-1 if the key is not pressed)
     */
    public double getKeyPressTime(char c) {
        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        if (result == null)
            return -1;

        Optional<KeyPress> k = result.findFirst();
        if (!k.isPresent())
            return -1;

        return k.get().buttonDownTime;
    }

    /**
     * This function will determine if the button has been "used" in the last timeframe. If it hasn't, it will set it
     * used and return true. Otherwise, it will return false. If the key is not found, it will also return false
     * @param c the key to check
     * @param minTime the minimum time needed to have passed in order for the button to be considered ready again
     * @return if the button was used again
     */
    public boolean isKeyTimedIn(char c, double minTime) {
        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        if (result == null)
            return false;

        Optional<KeyPress> k2 = result.findFirst();
        if (!k2.isPresent())
            return false;

        KeyPress k = k2.get();
        if (k.lastConsumeTime + minTime < System.currentTimeMillis()) {
            k.lastConsumeTime = System.currentTimeMillis();
            return true;
        }

        return false;
    }

}
