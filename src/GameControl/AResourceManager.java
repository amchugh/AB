package GameControl;

import java.util.ArrayList;

public class AResourceManager {

    protected ArrayList<AIDItem> items;

    public AResourceManager() {
        items = new ArrayList<>();
    }

    public int getNumberOfItems() {
        return items.size();
    }

    public void addItem(AIDItem i) {
        items.add(i);
    }

    public AIDItem getItemByID(int id) throws IllegalArgumentException {
        return items.stream().filter((i) -> i.getID() == id)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No item is loaded with ID " + id));
    }

    /**
     * This method will ensure that there are no conflicting ID numbers in the loaded dataset.
     * @return are all ID numbers unique
     */
    public boolean verifyIDNumbers() {
        ArrayList<Integer> usedIDs = new ArrayList<>();
        for (AIDItem i : items) {
            if (usedIDs.contains(i.getID())) {
                return false;
            }
            usedIDs.add(i.getID());
        }
        return true;
    }

}
