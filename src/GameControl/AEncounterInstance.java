package GameControl;

public class AEncounterInstance {

    private int id;
    private AEncounterEnvironment environment;
    private AEnemy enemy;

    public AEncounterInstance(int id, AEncounterEnvironment environment, AEnemy enemy) {
        this.id = id;
        this.environment = environment;
        this.enemy = enemy;
    }

    public int getId() {
        return id;
    }

    public AEncounterEnvironment getEnvironment() {
        return environment;
    }

    public AEnemy getEnemy() {
        return enemy;
    }
}
