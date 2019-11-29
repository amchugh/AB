package GameControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class ABPReader extends AIOJSONHelper {

    public ABPReader(String BPFilename) {
        super(BPFilename);
    }

    public ABP readABP(ABPSpeciesManager speciesManager, ABPActionManager actionManager) throws IOException, ParseException {
        JSONObject jo = getJSON();

        // Get the species
        int bpSpeciesID = getInt(jo, "SpeciesID");
        ABPSpecies species = speciesManager.getSpeciesByID(bpSpeciesID);

        // Construct the BP object
        ABP bp = new ABP(species);

        // Set the health
        int health = getInt(jo, "CurrentHP");
        bp.setHealth(health);

        // Load the moves
        Iterator actions = getArray(jo, "Actions");
        while (actions.hasNext()) {
            int actionID = getInt(actions.next());
            ABPAction a = actionManager.getActionByID(actionID);
            bp.addAction(a);
        }

        return bp;
    }

}
