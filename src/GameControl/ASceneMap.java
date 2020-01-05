package GameControl;

import java.awt.*;

/**
 * A concrete instance of AScene that handles the movement of the player character and all other elements
 * on the world map.
 */
public class ASceneMap implements AScene {

    private final Rectangle mapDisplayArea;
    private APlayerMapAvatar playerAvatar;
    private AMap map;

    public ASceneMap(APlayerMapAvatar playerAvatar, AMap map, Rectangle mapDisplayArea) {
        this.playerAvatar = playerAvatar;
        this.map = map;
        this.mapDisplayArea = mapDisplayArea;
    }

    @Override
    public void step() {
        playerAvatar.step();
        playerAvatar.handleMove();
        map.setViewFocus(playerAvatar.getGridPos());
        map.step();
    }

    @Override
    public void draw(Graphics g) {
        map.draw(g, mapDisplayArea);
    }

    @Override
    public boolean shouldScenePop() {
        return false; // todo:: this logic.
    }

    @Override
    public ASceneData shouldPushScene() {
        return map.shouldIntroduceNewScene();
    }
}
