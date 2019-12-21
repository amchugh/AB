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
            AResourceManager<ABPSpecies> m = new AResourceManager<ABPSpecies>(); // This does not need to be initialized.
                                                           // In fact, this could be null
            AResourceManager<ABPAction> m2 = new AResourceManager<>();
            r.readABP(m, m2);
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
    public void simpleReadExample() throws Exception, IOException, ParseException {
        ABPReader r = new ABPReader("test/rsc/SimpleBP.bpf");
        ABPSpeciesManagerReader mr = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        AResourceManager<ABPSpecies> m = mr.initializeSpeciesManager();
        ABPActionManagerReader ar = new ABPActionManagerReader("test/rsc/SimpleActionData.adf");
        AResourceManager<ABPAction> am = ar.initializeActionManager();
        ABP bp = r.readABP(m, am);

        assertNotNull(bp);
        assert (bp.getActions().size() == 1);
        assert (bp.getSpecies() != null);
    }

}
