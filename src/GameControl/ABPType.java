package GameControl;

import java.util.ArrayList;

public class ABPType extends AIDItem {

    private ArrayList<ABPType> weaknesses;
    private ArrayList<ABPType> immunities;

    public ABPType(int id) {
        super(id);
        weaknesses = new ArrayList<>();
        immunities = new ArrayList<>();
    }

    public void addWeakness(ABPType weakness) {
        weaknesses.add(weakness);
    }

    public void addImmunity(ABPType immune) {
        immunities.add(immune);
    }

    public boolean isWeakAgainst(ABPType o) {
        return doesContain(weaknesses, o);
    }

    public boolean isImmuneAgainst(ABPType o) {
        return doesContain(immunities, o);
    }

    private boolean doesContain(ArrayList a, Object o) {
        for (Object t :
                a) {
            if (t == o) return true;
        }
        return false;
    }

}
