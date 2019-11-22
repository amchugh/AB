package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AGameMainTest {

    private static final String
        testENData = "test/rsc/SimpleEnvironmentData.eef",
        testSPData = "test/rsc/SimpleSpeciesData.sdf",
        testACData = "test/rsc/SimpleActionData.adf";

    @Test
    void testLoadingAllResources() throws IOException, ParseException {
        AGameMain gm = new AGameMain();
        gm.setActionResourceName(testACData);
        gm.setEnvironmentResourceName(testENData);
        gm.setActionResourceName(testACData);
        gm.loadResources();
        assertTrue(gm.areResourcesLoaded(), "Resource Managers failed to initialize");
    }

}