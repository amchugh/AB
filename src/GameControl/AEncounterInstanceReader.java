package GameControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class AEncounterInstanceReader extends AIOJSONHelper {

    public AEncounterInstanceReader(String filename) {
        super(filename);
    }

    public AEncounterInstance loadEncounter(AEncounterEnvironmentManager envManager, ABPSpeciesManager speciesManager, ABPActionManager actionManager) throws IOException, ParseException {
        JSONObject jo = getJSON();

        int id = getInt(jo, "ID");
        int enemyId = getInt(jo, "EnemyID");
        int environmentID = getInt(jo, "EnvironmentID");

        // Load the enemy
        String enemyFilename = AGameMain.getEnemyFileNameFromID(enemyId);
        AEnemy enemy = (new AEnemyReader(enemyFilename)).loadEnemy(speciesManager, actionManager);

        // Load the environment
        AEncounterEnvironment env = envManager.getEnvironmentByID(environmentID);

        return new AEncounterInstance(id, env, enemy);
    }

}
