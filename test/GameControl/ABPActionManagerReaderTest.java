package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ABPActionManagerReaderTest {

    @Test
    public void throwsIOExceptionOnInvalidFilename() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
            AResourceManager<ABPType> tm = l.loadManager();
            ABPActionManagerReader r = new ABPActionManagerReader("rsc/doesnotexist.sdf");
            AResourceManager<ABPAction> m = r.initializeActionManager(tm);
        });
    }

    @Test
    public void throwsExceptionOnBadDataset() throws Exception {
        Assertions.assertThrows(IOException.class, () -> {
            ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
            AResourceManager<ABPType> tm = l.loadManager();
            ABPActionManagerReader r = new ABPActionManagerReader("test/rsc/BadActionData.adf");
            AResourceManager<ABPAction> m = r.initializeActionManager(tm);
        });
    }

    @Test
    public void simpleReadExample() throws Exception, IOException, ParseException {
        ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
        AResourceManager<ABPType> tm = l.loadManager();
        
        ABPActionManagerReader r = new ABPActionManagerReader("test/rsc/SimpleActionData.adf");
        AResourceManager<ABPAction> m = r.initializeActionManager(tm);

        assertNotNull(m);
        assert (m.getNumberOfItems() == 1);
    }

}
