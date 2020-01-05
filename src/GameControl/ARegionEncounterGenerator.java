package GameControl;

import java.util.Random;

/**
 * A simple implementation of the ARegion interface that supports a rectangular region of the map
 * that has a chance to spawn a single encounter.
 */
public class ARegionEncounterGenerator implements ARegion {
    private final GridPos topLeft, bottomRight;
    private final float chanceToCreateEncounter;
    private final int encounterInstanceID;
    private Random random;

    ARegionEncounterGenerator(float chanceToCreateEncounter, int encounterInstanceId,
                              GridPos topLeft, GridPos bottomRight) {
        assert (chanceToCreateEncounter >= 0 && chanceToCreateEncounter <= 1);
        this.chanceToCreateEncounter = chanceToCreateEncounter;
        this.encounterInstanceID = encounterInstanceId;
        this.random = new Random();
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public boolean containsGridPos(GridPos g) {
        return g.getX() >= topLeft.getX() && g.getX() <= bottomRight.getX() &&
                g.getY() >= topLeft.getY() && g.getY() <= bottomRight.getY();
    }

    public ASceneData shouldPushScene() {
        if (random.nextFloat() > chanceToCreateEncounter) {
            ASceneData d = new ASceneData();
            d.type = ASceneData.SceneTypes.ENCOUNTER;
            d.id = encounterInstanceID;
            return d;
        } else {
            return null;
        }
    }

    public int getEncounterId() {
        return encounterInstanceID;
    }

    public GridPos getTopLeft() {
        return topLeft;
    }

    public GridPos getBottomRight() {
        return bottomRight;
    }

    public float getChanceToEncounter() {
        return chanceToCreateEncounter;
    }
}
