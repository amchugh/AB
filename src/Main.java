import GameControl.*;

public class Main {

  // Resource File Locations
  private static final String DEFAULT_ENVIRONMENT_RESOURCE = "rsc/SimpleEnvironmentData.eef";
  private static final String DEFAULT_SPECIES_RESOURCE = "rsc/SimpleSpeciesData.sdf";
  private static final String DEFAULT_ACTION_RESOURCE = "rsc/SimpleActionData.adf";

  private static final String TEST_MAP_RESOURCE = "rsc/0.map";

  public static void main(String[] args) throws Exception{

    AGameMain main = new AGameMain();

    String environmentResource = DEFAULT_ENVIRONMENT_RESOURCE;
    String speciesResource = DEFAULT_SPECIES_RESOURCE;
    String actionResource = DEFAULT_ACTION_RESOURCE;

    AEncounterEnvironmentManager environmentManager;
    ABPActionManager actionManager;
    ABPSpeciesManager speciesManager;

    try {
      AEncounterEnvironmentManagerReader er = new AEncounterEnvironmentManagerReader(environmentResource);
      environmentManager = er.initializeEnvironmentManager();
    } catch (Exception e) {
      System.out.println("Unable to initialize environment manager");
      return;
    }

    try {
      ABPActionManagerReader ar = new ABPActionManagerReader(actionResource);
      actionManager = ar.initializeActionManager();
    } catch (Exception e) {
      System.out.println("Unable to initialize action manager");
      return;
    }

    try {
      ABPSpeciesManagerReader sr = new ABPSpeciesManagerReader(speciesResource);
      speciesManager = sr.initializeSpeciesManager();
    } catch (Exception e) {
      System.out.println("Unable to initialize species manager");
      return;
    }
    main.setup(); // This will set everything up for us

    // If two arguments are specified, we will assume that the first is the type of scene to load
    // and the second is the id number
    if (args.length == 2) {
      AGameMain.SceneTypes t;
      switch(args[0].toLowerCase()) {
        case "map": t = AGameMain.SceneTypes.MAP; break;
        case "test": t = AGameMain.SceneTypes.ENCOUNTER; break;
        default: throw new IllegalArgumentException("Bad scene type");
      }
      main.addScene(t, Integer.parseInt(args[1]));
    }

    // If one argument is specified than we will assume that it is the filename to load
    if (args.length == 1) {
      main.addSceneSmart(args[0]);
    }

    main.loop();
  }
}
