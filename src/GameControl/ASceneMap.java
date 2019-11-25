package GameControl;

import java.awt.*;

/**
 * A concrete instance of AScene that handles the movement of the player character and all other elements
 * on the world map.
 */
public class ASceneMap implements AScene {

    private APlayerCharacter player;
    private AMap map;

    public ASceneMap(APlayerCharacter player, AMap map) {
        this.player = player;
        this.map = map;
    }

    @Override
    public void step() {
        player.step();
        map.setViewFocus(player.getGridPos());
        map.getScene().step();
    }

    @Override
    public void draw(Graphics g) {
        map.getScene().draw(g);
    }
}
