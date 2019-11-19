package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ABPSpeciesManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("rsc/doesnotexist.sdf");
            ABPSpeciesManager m = r.initializeSpeciesManager();
            // It isn't okay if we have gotten this far!
            // TODO:  There is a better way to capture this sort of test that doesn't require the boilerplate.
            throw new Exception( "Test failure!");
        } catch ( IOException e ) {
            // Expected, so swallow
        } catch ( org.json.simple.parser.ParseException e ) {
            // Not expected
            throw e;
        }
    }

    @Test
    public void simpleReadExample() throws IOException, ParseException {
        ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        ABPSpeciesManager m = r.initializeSpeciesManager();
        // It isn'tABP bp = r.readABP(m);

        assert (m != null);
        assert (m.getNumberOfSpecies() == 1);
    }

}
