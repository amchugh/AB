package GameControl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * AUserInput is responsible for receiving key pressed events and providing a view into which keys are
 * currently down to the main thread.
 * <p>
 * Since the AUserInput will receive events from the UI thread and be asked questions about the state
 * of the world from another thread the public methods here are synchronized.  Using a synchronized collection
 * would be insufficient as we are modifying the collection during iteration which results in
 * ConcurrentModificationExceptions.
 */
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
        pressedKeys = new ArrayList<KeyPress>();
    }

    @Override
    synchronized public void keyPressed(KeyEvent e) {
        pressedKeys.add(new KeyPress(e.getKeyChar(), System.currentTimeMillis()));
    }

    @Override
    synchronized public void keyReleased(KeyEvent e) {
        pressedKeys.removeIf(s -> s.c == e.getKeyChar());
    }

    synchronized public boolean isKeyPressed(char c) {
        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        boolean finalResult = result != null && result.count() > 0;
        return finalResult;
    }

    /**
     * Returns if this is the first time the key has been consumed
     * @param c
     * @return
     */
    synchronized public boolean isKeyPressedFirstTime(char c) {
        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        if (result == null) {
            return false;
        }
        KeyPress k = result.findFirst().get();
        if (k.hasBeenConsumed) {
            return false;
        }
        // synchronized (k) {
        k.hasBeenConsumed = true;
        // }
        return true;
    }

    /**
     * Gets the time of the key press
     * @param c the key to find
     * @return the time in millis when the key was pressed (-1 if the key is not pressed)
     */
    synchronized public double getKeyPressTime(char c) {
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
    synchronized public boolean isKeyTimedIn(char c, double minTime) {
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
