package GameControl;

import java.util.ArrayList;

public class AEncounterEnvironmentManager extends AResourceManager{

    public AEncounterEnvironment getEnvironmentByID(int id) {
        return (AEncounterEnvironment) getItemByID(id);
    }

}
