package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ABPReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() {
        Assertions.assertThrows(IOException.class, () -> {
            ABPReader r = new ABPReader("rsc/DoesNotExists.map");
            AResourceManager<ABPSpecies> m = new AResourceManager<ABPSpecies>(); // This does not need to be initialized.
            // In fact, this could be null
            AResourceManager<ABPAction> m2 = new AResourceManager<>();
            r.readABP(m, m2);
        });
    }

    @Test
    public void simpleReadExample() throws Exception, IOException, ParseException {
        ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
        AResourceManager<ABPType> tm = l.loadManager();
        
        ABPReader r = new ABPReader("test/rsc/SimpleBP.bpf");
        ABPSpeciesManagerReader mr = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        AResourceManager<ABPSpecies> m = mr.initializeSpeciesManager(tm);
        ABPActionManagerReader ar = new ABPActionManagerReader("test/rsc/SimpleActionData.adf");
        AResourceManager<ABPAction> am = ar.initializeActionManager(tm);
        ABP bp = r.readABP(m, am);

        assertNotNull(bp);
        assert (bp.getActions().size() == 1);
        assert (bp.getSpecies() != null);
    }

}
