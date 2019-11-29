package GameControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class AEncounterEnvironmentManagerReader extends AIOJSONHelper {

    public AEncounterEnvironmentManagerReader(String filename) {
        super(filename);
    }

    public AEncounterEnvironmentManager initializeEnvironmentManager() throws IOException, ParseException {
        JSONObject jo = getJSON();
        AEncounterEnvironmentManager m = new AEncounterEnvironmentManager();

        Iterator ii = getIterator(jo, "Environments");

        while (ii.hasNext())
        {
            JSONObject oi = (JSONObject) ii.next();
            String mapFile = getString(oi, "BackgroundImage");
            int id = getInt(oi, "ID");
            AEncounterEnvironment environ = new AEncounterEnvironment(id, mapFile);
            m.addItem(environ);
        }

        if (!m.verifyIDNumbers()) {
            throw new IOException("Multiple Environments exist with the same ID");
        }

        return m;
    }

}
