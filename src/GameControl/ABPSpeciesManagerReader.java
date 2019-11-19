package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ABPSpeciesManagerReader extends AIOJSONHelper {

    public ABPSpeciesManagerReader(String filename) {
        super(filename);
    }

    public ABPSpeciesManager initializeSpeciesManager() throws Exception, IOException, ParseException {
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
            m.addItem(instance);
        }

        // todo:: find the more appropriate error to throw here
        if (!m.verifyIDNumbers()) {
            throw new Exception("Dataset contains duplicate ID numbers");
        }

        return m;
    }

}
