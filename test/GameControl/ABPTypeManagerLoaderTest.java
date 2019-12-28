package GameControl;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ABPTypeManagerLoaderTest {

    @Test
    void testLoadManager() throws IOException, ParseException {
        ABPTypeManagerLoader l = new ABPTypeManagerLoader("test/rsc/SimpleTypeData.tdf");
        AResourceManager<ABPType> m = l.loadManager();
        assert m.verifyIDNumbers();
        assert m.items.size() == 3;
    }
}