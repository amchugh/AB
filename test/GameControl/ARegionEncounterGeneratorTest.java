package GameControl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ARegionEncounterGeneratorTest {

    @Test
    public void testInclusion() {
        GridPos topLeft = new GridPos(1, 10);
        GridPos bottomRight = new GridPos(15, 12);

        ARegionEncounterGenerator g = new ARegionEncounterGenerator(1, 0, topLeft, bottomRight);

        // Clearly within
        assertTrue(g.containsGridPos(new GridPos(2, 11)));

        // On the boundary.
        assertTrue(g.containsGridPos(new GridPos(15, 11)));

        // On the bottom corner
        assertTrue(g.containsGridPos(new GridPos(15, 12)));

        // Not within
        assertFalse(g.containsGridPos(new GridPos(15, 9)));
    }

}