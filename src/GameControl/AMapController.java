package GameControl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

// fixme:: feel free to change this if you have a better idea on how to handle
public class AMapController extends KeyAdapter implements KeyListener, AForceProvider {
    private class KeyPress {
        char c;
        double buttonDownTime;

        KeyPress(char c, double t) {
            this.c = c;
            this.buttonDownTime = t;
        }
    }

    private Collection<KeyPress> pressedKeys;

    public AMapController() {
        // A synchronized collection is required here as there are multiple threads which
        // will be accessing the pressedKeys.  This is because the KeyListener interface
        // implemented here is exercised by the UI thread which is created by the window
        // and handles receiving and processing UI inputs.  This thread is separate from
        // the main thread that is automatically created in 'main' and which runs the main
        // game logic.  Without a synchronized data container then things go very poorly.
        pressedKeys = Collections.synchronizedCollection(new ArrayList<KeyPress>());
    }

    @Override
    public boolean isForcePresent(ForceDirection d) {
        return switch (d) {
            case UP -> isKeyPressed('w');
            case DOWN -> isKeyPressed('s');
            case LEFT -> isKeyPressed('a');
            case RIGHT -> isKeyPressed('d');
        };
    }

    @Override
    public int forceMagnitude(ForceDirection d) {
        if (isForcePresent(d)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed event; Key: " + e.getKeyChar());
        pressedKeys.add(new KeyPress(e.getKeyChar(), System.currentTimeMillis()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released event; Key: " + e.getKeyChar());
        pressedKeys.removeIf(s -> s.c == e.getKeyChar());
    }

    public boolean isKeyPressed(char c) {

        Stream<KeyPress> result = pressedKeys.stream().filter((i) -> i.c == c);
        boolean finalResult = result != null && result.count() > 0;

        // TODO:  Hack. Remove this.  This stops a key down from moving you across the map super super fast.
        pressedKeys.removeIf(s -> s.c == c);

        return finalResult;
    }
}
