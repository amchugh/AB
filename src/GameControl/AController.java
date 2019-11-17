package GameControl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

// fixme:: feel free to change this if you have a better idea on how to handle
public class AController extends KeyAdapter implements KeyListener {

    public enum ViableControls {UP, DOWN, LEFT, RIGHT, INTERACT};

    public AController() {
        pressedKeys = new ArrayList<>();
    }

    private class KeyPress {
        char c;
        double buttonDownTime;
        KeyPress (char _c, double _t) {
            c = _c;
            buttonDownTime = _t;
        }
    }

    private ArrayList<KeyPress> pressedKeys;

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(new KeyPress(e.getKeyChar(), System.currentTimeMillis()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // We need to remove all the keys with this char
        char s = e.getKeyChar();
        for (int i = 0; i < pressedKeys.size(); i++) {
            if (pressedKeys.get(i).c == s) {
                pressedKeys.remove(i);
                return;
            }
        }
    }

    public ArrayList<KeyPress> getSelected() {
        return pressedKeys;
    }

}
