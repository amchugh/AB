package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ABPReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            ABPReader r = new ABPReader("rsc/DoesNotExists.map");
            ABPSpeciesManager m = new ABPSpeciesManager(); // This does not need to be initialized.
                                                           // In fact, this could be null
            r.readABP(m);
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
    public void simpleReadExample() throws Exception, IOException, ParseException { //todo:: this method will need to be updated as well when a better exception is named
        ABPReader r = new ABPReader("test/rsc/SimpleBP.bpf");
        ABPSpeciesManagerReader mr = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        ABPSpeciesManager m = mr.initializeSpeciesManager();
        ABP bp = r.readABP(m);

        assertNotNull(bp);
        assert (bp.getSpecies() != null);
    }

}
