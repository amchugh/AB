
package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

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
        try {
            AMapReader r = new AMapReader("rsc/DoesNotExists.map");
            r.constructMap(null);
            // It isn't okay if we have gotten this far!
            // improveme:  There is a better way to capture this sort of test that doesn't require the boilerplate.
            throw new Exception( "Test failure!");
        } catch ( IOException e ) {
            // Expected, so swallow
        } catch ( ParseException e ) {
            // Not expected
            throw e;
        }
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
}
