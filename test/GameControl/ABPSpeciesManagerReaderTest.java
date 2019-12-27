package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ABPSpeciesManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("rsc/doesnotexist.sdf");
            AResourceManager<ABPSpecies> m = r.initializeSpeciesManager();
            // It isn't okay if we have gotten this far!
            // improveme:  There is a better way to capture this sort of test that doesn't require the boilerplate.
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
            AResourceManager<ABPSpecies> m = r.initializeSpeciesManager();
            // It isn't okay if we have gotten this far!
            // improveme:  There is a better way to capture this sort of test that doesn't require the boilerplate.
            throw new Exception( "Test failure!");
        } catch ( IOException e ) {
            // Is expected now, so swallow
        } catch ( org.json.simple.parser.ParseException e ) {
            // Not expected
            throw e;
        }
    }

    @Test
    public void simpleReadExample() throws Exception, IOException, ParseException {
        ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        AResourceManager<ABPSpecies> m = r.initializeSpeciesManager();

        assertNotNull(m);
        assert (m.getNumberOfItems() == 1);
    }

}
