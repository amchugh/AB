package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AEncounterEnvironmentManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            AEncounterEnvironmentManagerReader r = new AEncounterEnvironmentManagerReader("rsc/doesnotexist.sdf");
            AResourceManager<AEncounterEnvironment> m = r.initializeEnvironmentManager();
        });
    }

    @Test
    public void throwsExceptionOnBadDataset() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            AEncounterEnvironmentManagerReader r = new AEncounterEnvironmentManagerReader("test/rsc/BadEnvironmentData.eef");
            AResourceManager<AEncounterEnvironment> m = r.initializeEnvironmentManager();
        });
    }

    @Test
    public void simpleReadExample() throws Exception, IOException, ParseException {
        AEncounterEnvironmentManagerReader r = new AEncounterEnvironmentManagerReader("test/rsc/SimpleEnvironmentData.eef");
        AResourceManager<AEncounterEnvironment> m = r.initializeEnvironmentManager();

        assertNotNull(m);
        assert (m.getNumberOfItems() == 1);
    }

}
