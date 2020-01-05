package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ABPSpeciesManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
            AResourceManager<ABPType> tm = l.loadManager();
            ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("rsc/doesnotexist.sdf");
            AResourceManager<ABPSpecies> m = r.initializeSpeciesManager(tm);
        });
    }

    @Test
    public void throwsExceptionOnBadDataset() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
            AResourceManager<ABPType> tm = l.loadManager();
            ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("test/rsc/BadSpeciesData.sdf");
            AResourceManager<ABPSpecies> m = r.initializeSpeciesManager(tm);
        });
    }

    @Test
    public void simpleReadExample() throws Exception, IOException, ParseException {
        ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
        AResourceManager<ABPType> tm = l.loadManager();
        
        ABPSpeciesManagerReader r = new ABPSpeciesManagerReader("test/rsc/SimpleSpeciesData.sdf");
        AResourceManager<ABPSpecies> m = r.initializeSpeciesManager(tm);

        assertNotNull(m);
        assert (m.getNumberOfItems() == 1);
    }

}
