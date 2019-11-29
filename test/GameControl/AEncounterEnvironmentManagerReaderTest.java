package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AEncounterEnvironmentManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        try {
            AEncounterEnvironmentManagerReader r = new AEncounterEnvironmentManagerReader("rsc/doesnotexist.sdf");
            AResourceManager<AEncounterEnvironment> m = r.initializeEnvironmentManager();
            // It isn't okay if we have gotten this far!
            // TODO:  There is a better way to capture this sort of test that doesn't require the boilerplate.
            throw new Exception( "Test failure!");
        } catch ( IOException e ) {
            // unexpected
        } catch ( org.json.simple.parser.ParseException e ) {
            // Not expected
            throw e;
        }
    }

    @Test
    public void throwsExceptionOnBadDataset() throws Exception {
        try {
            AEncounterEnvironmentManagerReader r = new AEncounterEnvironmentManagerReader("test/rsc/BadEnvironmentData.eef");
            AResourceManager<AEncounterEnvironment> m = r.initializeEnvironmentManager();
            // It isn't okay if we have gotten this far!
            // TODO:  There is a better way to capture this sort of test that doesn't require the boilerplate.
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
        AEncounterEnvironmentManagerReader r = new AEncounterEnvironmentManagerReader("test/rsc/SimpleEnvironmentData.eef");
        AResourceManager<AEncounterEnvironment> m = r.initializeEnvironmentManager();

        assertNotNull(m);
        assert (m.getNumberOfItems() == 1);
    }

}
