package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class ABPActionManagerReader extends AIOJSONHelper {

    public ABPActionManagerReader(String filename) {
        super (filename);
    }

    public AResourceManager<ABPAction> initializeActionManager() throws IOException, ParseException {
        JSONObject jo = getJSON();
        AResourceManager<ABPAction> m = new AResourceManager<>();

        JSONArray items = (JSONArray) jo.get("Actions");
        Iterator ii = items.iterator();

        while (ii.hasNext())
        {
            JSONObject oi = (JSONObject) ii.next();
            int id = getInt(oi, "ID");
            int damage = getInt(oi, "Damage");
            String name = getString(oi, "Name");
            ABPAction action = new ABPAction(id, damage, name);
            m.addItem(action);
        }

        if (!m.verifyIDNumbers()) {
            throw new IOException("Multiple Actions exist with the same ID");
        }

        return m;
    }

}
