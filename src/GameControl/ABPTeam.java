package GameControl;

import java.util.ArrayList;
import java.util.List;

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

    public ABP getSelected() {
        return bpArray.get(currentlySelected);
    }

}
