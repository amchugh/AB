package GameControl;

public class ABPActionManager extends AResourceManager {

    public ABPAction getActionByID(int id) {
        return (ABPAction) getItemByID(id);
    }

}
