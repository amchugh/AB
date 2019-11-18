package GameControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AIOManager {
    private String filename;

    AIOManager(String filename) {
        this.filename = filename;
    }

    protected FileReader getFile() throws FileNotFoundException {
        return new FileReader(filename);
    }

    protected JSONObject getJSON() throws IOException, ParseException {
        return (JSONObject)(new JSONParser().parse(getFile()));
    }

    protected int getInt(JSONObject o, String sub) {
        return ((Long)o.get(sub)).intValue();
    }

    protected int getInt(Object o) {
        return ((Long)o).intValue();
    }

}
