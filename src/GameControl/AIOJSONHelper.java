package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class AIOJSONHelper {
    private String filename;

    AIOJSONHelper(String filename) {
        this.filename = filename;
    }

    protected FileReader getFile() throws FileNotFoundException {
        return new FileReader(filename);
    }

    protected JSONObject getJSON() throws IOException, ParseException {
        FileReader f = getFile();
        JSONObject r = (JSONObject)(new JSONParser().parse(f));
        f.close();
        return r;
    }

    protected int getInt(JSONObject o, String sub) {
        return ((Long)o.get(sub)).intValue();
    }
    
    protected float getFloat(JSONObject o, String sub) {
        return ((Double) o.get(sub)).floatValue();
    }
    
    protected double getDouble(JSONObject o, String sub) {
        return ((Double) o.get(sub));
    }
    
    protected int getInt(Object o) {
        return ((Long)o).intValue();
    }

    protected String getString(JSONObject o, String sub) {
        return (String)o.get(sub);
    }

    protected JSONArray getArray (JSONObject o, String sub) { return (JSONArray)o.get(sub); }
    protected Iterator getIterator(JSONObject o, String sub) {
        return ((JSONArray) o.get(sub)).iterator();
    }

    protected boolean doesFieldExist(JSONObject o, String sub) {
        return o.containsKey(sub);
    }
    
    /**
     * Given an appropriate JSON object containing stat information, load it and return a stats instance
     * @param o the object containing the stats
     * @return the loaded stats
     */
    protected AStats loadStats(JSONObject o) {
        AStats s = new AStats();
        s.hitpoints = getDouble(o, "HP");
        s.armorStrength = getDouble(o, "AS");
        s.armorDurability = getDouble(o, "AD");
        s.attackPower = getDouble(o, "AP");
        s.attackPierce = getDouble(o, "APP");
        s.speed = getDouble(o, "SP");
        s.endurance = getDouble(o, "ED");
        return s;
    }
    
}
