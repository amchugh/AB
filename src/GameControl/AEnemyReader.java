package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class AEnemyReader extends AIOJSONHelper {

    public AEnemyReader(String filename) {
        super(filename);
    }

    public AEnemy loadEnemy(ABPSpeciesManager m, ABPActionManager a) throws IOException, ParseException {
        JSONObject jo = getJSON();

        String deathText = getString(jo, "DeathText");

        AEnemy e = new AEnemy(deathText);

        JSONArray items = (JSONArray) jo.get("BPs");
        Iterator bps = items.iterator();

        while (bps.hasNext()) {
            int bpID = getInt(bps.next());
            String bpFilename = AGameMain.getBPDataFileNameFromID(bpID);
            ABP c = (new ABPReader(bpFilename)).readABP(m, a);
            e.addBP(c);
        }

        return e;
    }

}
