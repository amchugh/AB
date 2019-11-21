package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AGameMainTest {

    private static final String
        testENData = "test/rsc/SimpleEnvironmentData.eef",
        testSPData = "test/rsc/SimpleSpeciesData.sdf",
        testACData = "test/rsc/SimpleActionData.adf";

    @Test
    public void testLoadingAllResources() throws IOException, ParseException {
        AGameMain gm = new AGameMain(false); // Calling with false because we want
                                                     // to setup our own way
        gm.loadResources(testENData, testACData, testSPData);
        assertTrue(gm.areResourcesLoaded(), "Resource Managers failed to initialize");
    }

}