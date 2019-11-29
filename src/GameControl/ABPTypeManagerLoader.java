package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class ABPTypeManagerLoader extends AIOJSONHelper {

    public ABPTypeManagerLoader(String filename) {
        super(filename);
    }

    public AResourceManager<ABPType> loadManager() throws IOException, ParseException {
        JSONObject jo = getJSON();
        AResourceManager<ABPType> m = new AResourceManager<>();
        JSONArray arr = getArray(jo, "Types");

        // First, create the types
        for (int i = 0; i < arr.size(); i++) {
            m.addItem(new ABPType(i));
        }

        // Now, register the weaknesses and immunities
        for (int i = 0; i < arr.size(); i++) {
            ABPType type = (ABPType)m.getItemByID(i);

            JSONObject t = (JSONObject)arr.get(i);
            String name = getString(t, "Name");
            JSONArray weaknesses = getArray(t, "Weaknesses");
            JSONArray immunities = getArray(t, "Immunities");

            for (Object weaknessID :
                    weaknesses) {
                ABPType w = (ABPType)m.getItemByID(getInt(weaknessID));
                type.addWeakness(w);
            }

            for (Object immunityID :
                    immunities) {
                ABPType w = (ABPType)m.getItemByID(getInt(immunityID));
                type.addImmunity(w);
            }
        }

        return m;
    }

}
