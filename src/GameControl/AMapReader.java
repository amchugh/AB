package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

public class AMapReader extends AIOJSONHelper {

    public AMapReader(String mapFileName) {
        super(mapFileName);
    }

    AMap constructMap(ACellManager cellManager) throws IOException, ParseException {
        JSONObject jo = getJSON();

        int mapId = getInt(jo, "MapId");
        int width = getInt(jo, "Width");
        int height = getInt(jo, "Height");

        AMapInstance map = new AMapInstance(mapId, width, height, cellManager);

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
}
