package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ABPReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            ABPReader r = new ABPReader("rsc/DoesNotExists.map");
            ABPSpeciesManager m = new ABPSpeciesManager(); // TODO initialize Species Manager
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
    public void simpleReadExample() throws IOException, ParseException {
        ABPReader r = new ABPReader("test/rsc/SimpleBP.bpf");
        ABPSpeciesManager m = new ABPSpeciesManager(); // TODO initialize Species Manager
        ABP bp = r.readABP(m);

        assert (bp != null);
        assert (bp.getSpecies() != null); // TODO this will be more important later
    }

}
