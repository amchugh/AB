package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class AMapReader {
    private String mapFileName;

    AMapReader(String _mapFileName) {
        mapFileName = _mapFileName;
    }

    AMap constructMap() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(mapFileName));
        JSONObject jo = (JSONObject) obj;

        int mapId = getInt(jo, "MapId");
        int width = getInt(jo, "Width");
        int height = getInt(jo, "Height");

        AMapInstance map = new AMapInstance(mapId, width, height);

        // Get the cell array.  This will be an array of arrays.
        JSONArray cells = (JSONArray) jo.get("Cells");
        Iterator rowIterator = cells.iterator();

        int rows = 0;
        int cols;
        while (rowIterator.hasNext())
        {
            Iterator colIterator = ((JSONArray) rowIterator.next()).iterator();
            cols = 0;
            while (colIterator.hasNext()) {
                int cellId = getInt(colIterator.next());
                map.setCell(rows, cols, cellId);
                cols += 1;
            }
            rows += 1;
        }
        return map;
    }

    private int getInt(JSONObject o, String sub) {
        return ((Long)o.get(sub)).intValue();
    }

    private int getInt(Object o) {
        return ((Long)o).intValue();
    }
}
