package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.Iterator;

public class ABPSpeciesManagerReader extends AIOManager {

    public ABPSpeciesManagerReader(String filename) {
        super(filename);
    }

    ABPSpeciesManager initializeSpeciesManager() throws IOException, ParseException {
        JSONObject jo = getJSON();

        ABPSpeciesManager m = new ABPSpeciesManager();

        JSONArray species = (JSONArray) jo.get("Species");
        Iterator i = species.iterator();

        while (i.hasNext()) {
            JSONObject s = (JSONObject) i.next();
            int id = getInt(s, "ID");
            String frontImageName = getString(s, "FrontImageLocation");
            String backImageName = getString(s, "BackImageLocation");
            ABPSpecies instance = new ABPSpecies(id, frontImageName, backImageName);
            m.add(instance);
        }

        return m;
    }

}
