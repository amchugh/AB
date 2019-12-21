import GameControl.Tools.AMapEditorMain;

// The controlling program for the map editor.  Kept sparse on purpose.

public class MapEditorMain {

    private static final String TEST_MAP_RESOURCE = "rsc/Maps/0.map";

    public static void main(String[] args) throws Exception {
        // TODO:  Add in an necessary argument passing here - for example the map name.
        AMapEditorMain main = new AMapEditorMain(TEST_MAP_RESOURCE);
        main.loop();
    }
}
