package GameControl;

import java.awt.*;

/**
 * A concrete instance of AScene that handles the movement of the player character and all other elements
 * on the world map.
 */
public class ASceneMap implements AScene {

    private APlayerMapAvatar playerAvatar;
    private AMap map;

    public ASceneMap(APlayerMapAvatar playerAvatar, AMap map) {
        this.playerAvatar = playerAvatar;
        this.map = map;
    }

    @Override
    public void step() {
        playerAvatar.step();
        playerAvatar.handleMove();
        map.setViewFocus(playerAvatar.getGridPos());
        map.getScene().step();
    }

    @Override
    public void draw(Graphics g) {
        map.getScene().draw(g);
    }

    @Override
    public boolean shouldScenePop() {
        return false; // todo:: this logic.
    }

    @Override
    public ASceneData shouldPushScene() {
        return null; // todo:: this logic.
    }
}
