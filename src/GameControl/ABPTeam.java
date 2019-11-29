package GameControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ABPTeam {

    public static final int MAX_TEAMSIZE = 6;

    private ArrayList<ABP> bpArray;
    private int currentlySelected = 0;

    public ABPTeam() {
        bpArray = new ArrayList<>();
    }

    public void addBP(ABP member) throws IndexOutOfBoundsException {
        if (bpArray.size() < MAX_TEAMSIZE) {
            bpArray.add(member);
            return;
        } else {
            throw new IndexOutOfBoundsException("Team is already full");
        }
    }

    public void setSelection(int newSel) {
        if (newSel > MAX_TEAMSIZE) {
            throw new IndexOutOfBoundsException("Index (" + newSel + ") is not valid; greater than max teamsize");
        } else if (newSel > bpArray.size() - 1) {
            throw new IndexOutOfBoundsException("Index (" + newSel + ") is not valid");
        }
        currentlySelected = newSel;
    }

    public void setSelection(ABP selection) {
        for (int i = 0; i < bpArray.size(); i++) {
            if (bpArray.get(i) == selection) {
                currentlySelected = i;
                return;
            }
        }
        throw new RuntimeException("Attempted to select a BP not in the team");
    }

    public ABP getSelected() {
        if (bpArray.size() == 0) {
            throw new RuntimeException("BP array has not been setup (there are no BPs added to this Team)");
        }
        return bpArray.get(currentlySelected);
    }

    /**
     * @return if there is a BP with positive health
     */
    public boolean hasLivingBP() {
        Stream<ABP> abpStream = bpArray.stream().filter(b -> b.isAlive());
        return !((abpStream == null) || (abpStream.count() == 0));
    }

    public List<ABP> getLivingBP() {
        Stream<ABP> abpStream = bpArray.stream().filter(b -> b.isAlive());
        return abpStream.collect(Collectors.toList());
    }

}
