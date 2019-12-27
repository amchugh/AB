package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ABPActionManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            ABPActionManagerReader r = new ABPActionManagerReader("rsc/doesnotexist.sdf");
            AResourceManager<ABPAction> m = r.initializeActionManager();
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
            ABPActionManagerReader r = new ABPActionManagerReader("test/rsc/BadActionData.adf");
            AResourceManager<ABPAction> m = r.initializeActionManager();
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
        ABPActionManagerReader r = new ABPActionManagerReader("test/rsc/SimpleActionData.adf");

        AResourceManager<ABPAction> m = r.initializeActionManager();

        assertNotNull(m);
        assert (m.getNumberOfItems() == 1);
    }

}
