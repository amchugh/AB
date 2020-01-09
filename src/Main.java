import GameControl.*;

public class Main {

  // Resource File Locations
  private static final String DEFAULT_ENVIRONMENT_RESOURCE = "rsc/SimpleEnvironmentData.eef";
  private static final String DEFAULT_SPECIES_RESOURCE = "rsc/SimpleSpeciesData.sdf";
  private static final String DEFAULT_ACTION_RESOURCE = "rsc/SimpleActionData.adf";
  private static final String DEFAULT_TYPE_RESOURCE = "rsc/SimpleTypeData.tdf";

  private static final String TEST_MAP_RESOURCE = "rsc/Maps/0.map";

  public static void main(String[] args) throws Exception{

    AGameMain main = new AGameMain();

    String environmentResource = DEFAULT_ENVIRONMENT_RESOURCE;
    String speciesResource = DEFAULT_SPECIES_RESOURCE;
    String actionResource = DEFAULT_ACTION_RESOURCE;
    String typeResource = DEFAULT_TYPE_RESOURCE;

    AResourceManager<AEncounterEnvironment> environmentManager;
    AResourceManager<ABPAction> actionManager;
    AResourceManager<ABPSpecies> speciesManager;
    AResourceManager<ABPType> typeManager;

    // Load all the managers
    try {
      AEncounterEnvironmentManagerReader er = new AEncounterEnvironmentManagerReader(environmentResource);
      environmentManager = er.initializeEnvironmentManager();
    } catch (Exception e) {
      System.out.println("Unable to initialize environment manager");
      throw new RuntimeException(e);
    }
  
    // Type manager needs to be loaded before the species and action managers as they depend on it
    try {
      ABPTypeManagerLoader tr = new ABPTypeManagerLoader(typeResource);
      typeManager = tr.loadManager();
    } catch (Exception e) {
      System.out.println("Unable to initialize type manager");
      throw new RuntimeException(e);
    }
    
    try {
      ABPActionManagerReader ar = new ABPActionManagerReader(actionResource);
      actionManager = ar.initializeActionManager(typeManager);
    } catch (Exception e) {
      System.out.println("Unable to initialize action manager");
      throw new RuntimeException(e);
    }

    try {
      ABPSpeciesManagerReader sr = new ABPSpeciesManagerReader(speciesResource);
      speciesManager = sr.initializeSpeciesManager(typeManager);
    } catch (Exception e) {
      System.out.println("Unable to initialize species manager");
      throw new RuntimeException(e);
    }
    
    // Set the managers in main
    main.setBPActionManager(actionManager);
    main.setBPSpeciesManager(speciesManager);
    main.setEncounterEnvironmentManager(environmentManager);
    main.setBPTypeManager(typeManager);
    main.setup(); // This will set everything up for us

    // If two arguments are specified, we will assume that the first is the type of scene to load
    // and the second is the id number
    if (args.length == 2) {
      main.addScene(
              switch (args[0].toLowerCase()) {
                case "map" -> ASceneData.SceneTypes.MAP;
                case "encounter" -> ASceneData.SceneTypes.ENCOUNTER;
                case "splash" -> ASceneData.SceneTypes.SPLASH;
                default -> throw new IllegalStateException("Unexpected value: " + args[0].toLowerCase());
              }, Integer.parseInt(args[1]));
    } else if (args.length == 1) {
      // If one argument is specified than we will assume that it is the filename to load
      main.addSceneSmart(args[0]);
    }

    main.loop();
  }
}
