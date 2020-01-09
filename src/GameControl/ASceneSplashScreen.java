package GameControl;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * A concrete instance of AScene that shows an image on the screen and waits for the user to hit any key.
 * This is used as an intro screen when starting the game.   It could be generalized more in the future.
 */
public class ASceneSplashScreen implements AScene {

    private final AUserInput userInput;
    private final int mapId;
    private Image backgroundImage;
    private static final String DEFAULT_BACKGROUND_FILENAME = "rsc/images/Splash.png";

    public ASceneSplashScreen(AUserInput userInput, int mapId) throws IOException {
        this.backgroundImage = read(new File(DEFAULT_BACKGROUND_FILENAME));
        this.userInput = userInput;
        this.mapId = mapId;
    }

    @Override
    public void step() {
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0,
                backgroundImage.getWidth(null),
                backgroundImage.getHeight(null),
                null);
    }

    @Override
    public boolean shouldScenePop() {
        return false;
    }

    @Override
    public ASceneData shouldPushScene() {
        if (userInput.anyKeysPressed()) {
            ASceneData d = new ASceneData();
            d.type = ASceneData.SceneTypes.MAP;
            d.id = mapId;
            return d;
        } else {
            return null;
        }
    }
}
