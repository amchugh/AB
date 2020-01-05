
package GameControl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

class AMapWriterTest {
    @Mock
    private ACellManager cellManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReadThenWriteThenReadAgain() throws Exception {
        Random r = new Random();
        String randomFilename = String.format("test/rsc/Temp-%s.%s.map",
                new SimpleDateFormat("ddMMyy-hhmmss").format(new Date()),
                r.nextInt(9));
        try {
            Mockito.when(cellManager.isCellIdValid(anyInt())).thenReturn(true);

            AMapReader reader = new AMapReader("test/rsc/MapWithRegions.map");
            // Cast here makes the test non-portable.
            AMapInstance m = (AMapInstance) reader.constructMap(cellManager);

            // Try writing to the randomFileName and then reading again.
            AMapWriter writer = new AMapWriter(randomFilename);
            writer.writeMap(m);

            // Read back again and see how they original compares.
            AMapReader reader2 = new AMapReader(randomFilename);
            AMapInstance m2 = (AMapInstance) reader2.constructMap(cellManager);

            assertEquals(m.getGridHeight(), m2.getGridHeight());
            assertEquals(m.getGridWidth(), m2.getGridWidth());
            assertEquals(m.getRegions().size(), m2.getRegions().size());

        } finally {
            File f = new File(randomFilename);
            Files.deleteIfExists(f.toPath());
        }
    }
}
