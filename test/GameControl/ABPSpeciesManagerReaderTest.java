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
    public void throwsExceptionOnBadDataset() throws Exception {
        try {
            ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("test/rsc/BadSpeciesData.sdf");
            ABPSpeciesManager m = r.initializeSpeciesManager();
            // It isn't okay if we have gotten this far!
            // TODO:  There is a better way to capture this sort of test that doesn't require the boilerplate.
            throw new Exception( "Test failure!");
        } catch ( IOException e ) {
            // not expected
            throw e;
        } catch ( org.json.simple.parser.ParseException e ) {
            // Not expected
            throw e;
        } catch (Exception e) { // todo:: update when a more specific error is named in the tested class
            // Swallow this one
        }
    }

    @Test
    public void simpleReadExample() throws Exception, IOException, ParseException {
        ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        ABPSpeciesManager m = r.initializeSpeciesManager();
        // It isn'tABP bp = r.readABP(m);

        assert (m != null);
        assert (m.getNumberOfSpecies() == 1);
    }

}
