package GameControl;

import java.util.ArrayList;

public class ABPSpeciesManager extends AResourceManager {

    public ABPSpecies getSpeciesByID(int id) throws IllegalArgumentException {
        return (ABPSpecies) getItemByID(id);
    }

}
