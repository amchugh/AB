
package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AMapReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            AMapReader r = new AMapReader("rsc/DoesNotExists.map");
            r.constructMap(null);
            // It isn't okay if we have gotten this far!
            // TODO:  There is a better way to capture this sort of test that doesn't require the boilerplate.
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
        AMapReader r = new AMapReader("test/rsc/SimpleMap.map");
        AMap m = r.constructMap(new ACellManagerSimple());

        assert (m != null);
    }
}
