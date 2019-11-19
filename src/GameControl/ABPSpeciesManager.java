package GameControl;

import java.util.ArrayList;

public class ABPSpeciesManager {

    private ArrayList<ABPSpecies> species;

    public ABPSpeciesManager() {
        species = new ArrayList<>();
    }

    public ABPSpecies getSpeciesByID(int id) {
        // TODO add proper implementation
        return new ABPSpecies();
    }

    public void add(ABPSpecies s) {
        species.add(s);
    }

    public int getNumberOfSpecies() {
        return species.size();
    }

}
