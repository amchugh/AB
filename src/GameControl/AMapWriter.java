package GameControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class AMapWriter {

    private String mapFileName;

    public AMapWriter(String mapFileName) {
        this.mapFileName = mapFileName;
    }

    public void writeMap(AMapInstance map) {
        JSONObject result = new JSONObject();
        result.put("MapId", map.getGridId());
        result.put("Width", map.getGridWidth());
        result.put("Height", map.getGridHeight());

        JSONArray rows = new JSONArray();
        for (int r = 0; r < map.getGridHeight(); r++) {
            JSONArray col = new JSONArray();
            for (int c = 0; c < map.getGridWidth(); c++) {
                col.add(map.getCell(r, c));
            }
            rows.add(col);
        }
        result.put("Cells", rows);
        try (FileWriter file = new FileWriter(mapFileName)) {
            file.write(result.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected failure", e);
        }
    }
}
