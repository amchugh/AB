package GameControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ABPReader extends AIOJSONHelper {

    public ABPReader(String BPFilename) {
        super(BPFilename);
    }

    public ABP readABP(ABPSpeciesManager speciesManager) throws IOException, ParseException {
        JSONObject jo = getJSON();

        int bpSpeciesID = getInt(jo, "SpeciesID");
        int health = getInt(jo, "CurrentHP");
        ABPSpecies species = speciesManager.getSpeciesByID(bpSpeciesID);

        ABP bp = new ABP();
        bp.setHealth(health);

        return bp;
    }

}
