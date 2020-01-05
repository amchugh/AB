
package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;

class AMapReaderTest {
    @Mock
    private ACellManager cellManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            AMapReader r = new AMapReader("rsc/DoesNotExists.map");
            r.constructMap(null);
        });
    }

    @Test
    public void simpleReadExample() throws IOException, ParseException {
        // Our cellManager has a fixed number of cellIds defined here and we want
        // to allow the test file to use any cellId.
        Mockito.when(cellManager.isCellIdValid(anyInt())).thenReturn(true);

        AMapReader r = new AMapReader("test/rsc/SimpleMap.map");
        AMap m = r.constructMap(cellManager);

        assertNotNull(m);
    }

    @Test
    public void mapWithRegions() throws IOException, ParseException {
        // Our cellManager has a fixed number of cellIds defined here and we want
        // to allow the test file to use any cellId.
        Mockito.when(cellManager.isCellIdValid(anyInt())).thenReturn(true);

        AMapReader r = new AMapReader("test/rsc/MapWithRegions.map");
        AMap m = r.constructMap(cellManager);

        assertNotNull(m);

        // Peek under the hood now.  This will fail once the map reader returns a different type of AMap implementation
        // and this test will need to be augmented.
        AMapInstance peeker = (AMapInstance) m;
        assertEquals(2, peeker.getRegions().size());

        ARegionEncounterGenerator g = peeker.getRegions().get(1);
        assertEquals(50, g.getTopLeft().getX());
        assertEquals(51, g.getTopLeft().getY());
        assertEquals(55, g.getTopLeft().getX());
        assertEquals(56, g.getTopLeft().getY());
        assertEquals(0.01, g.getChanceToEncounter());
        assertEquals(101, g.getEncounterId());
    }
}
