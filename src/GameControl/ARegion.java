package GameControl;

/**
 * A region is a portion of a map that has arbitrary boundaries and can influence the game through
 * random encounters, character effects or other things happening within the game.
 */
public interface ARegion {

    /**
     * A region may have arbitrary boundaries.  This method will return true if the grid position passed in
     * is contained within this specific region.
     */
    boolean containsGridPos(GridPos g);

    /**
     * A region may decide that a new scene needs to be inserted into the scene stack.  This method may return null
     * if no scene should be pushed.
     *
     * @return a new scene data instance to pushed to the top of the stack or null if no scene is forthcoming.
     */
    ASceneData shouldPushScene();
}
