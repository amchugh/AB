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

    public AMap constructMap(ACellManager cellManager) throws IOException, ParseException {
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

        JSONArray regions = (JSONArray) jo.get("Regions");
        if (regions != null) {
            Iterator regionIterator = regions.iterator();
            while (regionIterator.hasNext()) {
                JSONObject regionSpec = (JSONObject) regionIterator.next();
                int topLeftX = getInt(regionSpec, "TopLeftX");
                int topLeftY = getInt(regionSpec, "TopLeftY");
                int bottomRightX = getInt(regionSpec, "BottomRightX");
                int bottomRightY = getInt(regionSpec, "BottomRightY");
                int encounterId = getInt(regionSpec, "EncounterId");
                float chanceToEncounter = getFloat(regionSpec, "ChanceToEncounter");

                map.addRegion(new ARegionEncounterGenerator(chanceToEncounter, encounterId, new GridPos(topLeftX, topLeftY), new GridPos(bottomRightX, bottomRightY)));
            }
        }
        return map;
    }
}
