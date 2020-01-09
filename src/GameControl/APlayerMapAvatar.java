package GameControl;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class APlayerMapAvatar {
    private APlayerCharacter player;
    private GridPos currentPos;
    private AForceProvider movementProvider;
    private AGridPosValidator gridPosValidator;
    private Image playerImage;

    private static final String DEFAULT_PLAYER_FILENAME = "rsc/images/Player.png";

    public APlayerMapAvatar(APlayerCharacter player, AForceProvider forceProvider) throws IOException {
        this.player = player;
        this.movementProvider = forceProvider;
        currentPos = new GridPos(1, 1);
        playerImage = read(new File(DEFAULT_PLAYER_FILENAME));
    }

    public void setGridPosValidator(AGridPosValidator gridPosValidator) {
        this.gridPosValidator = gridPosValidator;
    }

    public GridPos getGridPos() {
        return currentPos;
    }

    public void step() {
    }

    public void handleMove() {
        GridPos desiredPos = new GridPos(currentPos.getX(), currentPos.getY());
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.UP)) {
            desiredPos.setY(desiredPos.getY() - 1);
        }
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.DOWN)) {
            desiredPos.setY(desiredPos.getY() + 1);
        }
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.LEFT)) {
            desiredPos.setX(desiredPos.getX() - 1);
        }
        if (movementProvider.isForcePresent(AForceProvider.ForceDirection.RIGHT)) {
            desiredPos.setX(desiredPos.getX() + 1);
        }
        if (!(desiredPos.equals(currentPos))) {
            // System.out.println("Evaluating moved position; newPos: " + desiredPos.getX() + ", " + desiredPos.getY());
            if (gridPosValidator.isGridPosOccupiable(desiredPos)) {
                currentPos = desiredPos;
                System.out.println("Setting currentPos to be; " + currentPos.getX() + ", " + currentPos.getY());
            }
        }
    }

    public void draw(Graphics g) {
        // Jason :: TODO:  Magic constants are for losers
        g.drawImage(playerImage, currentPos.getX() * 32, currentPos.getY() * 32,
                playerImage.getWidth(null),
                playerImage.getHeight(null),
                null);
    }
}
