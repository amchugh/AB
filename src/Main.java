import GameControl.*;

public class Main {

  // Resource File Locations
  private static final String DEFAULT_ENVIRONMENT_RESOURCE = "rsc/SimpleEnvironmentData.eef";
  private static final String DEFAULT_SPECIES_RESOURCE = "rsc/SimpleSpeciesData.sdf";
  private static final String DEFAULT_ACTION_RESOURCE = "rsc/SimpleActionData.adf";


  public static void main(String[] args) {

    AGameMain main = new AGameMain();

    // TODO:  Add in here full CLI argument parsing.
    // TODO:  Shift more of the creation of the dependent things used by GameMain into here and follow
    //  the inversion of control -- dependency injection -- design pattern.
    if (args.length == 1 && args[0].equalsIgnoreCase("Map")) {
      main.setStartWithMap(true);
    }

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
    main.go();
  }
}
