package GameControl;

import java.util.ArrayList;

public class ABPSpeciesManager {

    private ArrayList<ABPSpecies> species;

    public ABPSpeciesManager() {
        species = new ArrayList<>();
    }

    public ABPSpecies getSpeciesByID(int id) throws IllegalArgumentException {
        // todo:: I would love for this to be implemented functionally. Unfortunately, I don't know
        // todo:: enough about functional java programming to do that.
        for (ABPSpecies spec : species) {
            if (spec.getID() == id) {
                return spec;
            }
        }
        throw new IllegalArgumentException("No species is loaded with ID " + id);
    }

    public void add(ABPSpecies s) {
        species.add(s);
    }

    public int getNumberOfSpecies() {
        return species.size();
    }

    /**
     * This method will ensure that there are no conflicting ID numbers in the loaded dataset.
     * @return are all ID numbers unique
     */
    public boolean verifyIDNumbers() {
        ArrayList<Integer> usedIDs = new ArrayList<>();
        for (ABPSpecies spec : species) {
            if (usedIDs.contains(spec.getID())) {
                return false;
            }
            usedIDs.add(spec.getID());
        }
        return true;
    }


}
