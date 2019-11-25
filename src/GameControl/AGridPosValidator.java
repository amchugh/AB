package GameControl;

/**
 * This construct is responsible for answering basic questions about grid positions within the current
 * Map.
 */
public interface AGridPosValidator {
    boolean isGridPosOccupiable(GridPos g);
}
