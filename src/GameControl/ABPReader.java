package GameControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ABPReader extends AIOJSONHelper {

    public ABPReader(String BPFilename) {
        super(BPFilename);
    }
    
    public ABP readABP(
        AResourceManager<ABPSpecies> speciesManager,
        AResourceManager<ABPAction> actionManager
    ) throws IOException, ParseException {
        
        JSONObject jo = getJSON();

        // Get the species
        int bpSpeciesID = getInt(jo, "SpeciesID");
        ABPSpecies species = speciesManager.getItemByID(bpSpeciesID);

        // Get the BP stats
        JSONObject stats = (JSONObject)jo.get("Stats");
        AStats s = loadStats(stats);
        
        // Get the XP
        int xp = getInt(jo, "XP");
        
        // Construct the BP object
        ABP bp = new ABP(species, s, xp);

        // See if there is a CustomName set
        if (doesFieldExist(jo, "CustomName")) {
            String customName = getString(jo, "CustomName");
            bp.setCustomName(customName);
        }

        // Set the health
        int health = getInt(jo, "CurrentHP");
        bp.setHealth(health);

        // Load the moves
        Iterator actions = getIterator(jo, "Actions");
        while (actions.hasNext()) {
            int actionID = getInt(actions.next());
            ABPAction a = actionManager.getItemByID(actionID);
            bp.addAction(a);
        }

        return bp;
    }

}
