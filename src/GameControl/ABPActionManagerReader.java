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

    public ABPActionManager initializeActionManager() throws IOException, ParseException {
        JSONObject jo = getJSON();
        ABPActionManager m = new ABPActionManager();

        JSONArray items = (JSONArray) jo.get("Actions");
        Iterator ii = items.iterator();

        while (ii.hasNext())
        {
            JSONObject oi = (JSONObject) ii.next();
            int id = getInt(oi, "ID");
            int damage = getInt(oi, "Damage");
            ABPAction action = new ABPAction(id, damage);
            m.addItem(action);
        }

        if (!m.verifyIDNumbers()) {
            throw new IOException("Multiple Environments exist with the same ID");
        }

        return m;
    }

}