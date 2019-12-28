package ResourceEditor;

import GameControl.*;

public class AResourceEditorMain {
    
    // Store the file locations for all the managers
    private String environmentResource;
    private String speciesResource;
    private String actionResource;
    private String typeResource;
    
    // We will need a ResourceManager for every resource in the game currently
    private AResourceManager<ABPType> typeManager;
    private AResourceManager<AEncounterEnvironment> environmentManager;
    private AResourceManager<ABPSpecies> speciesManager;
    private AResourceManager<ABPAction> actionManager;
    
    public void setBPActionResource(String f) {
        actionResource = f;
        assert typeManager != null;
        try {
            ABPActionManagerReader ar = new ABPActionManagerReader(actionResource);
            actionManager = ar.initializeActionManager(typeManager);
        } catch (Exception e) {
            System.out.println("Unable to initialize action manager");
            throw new RuntimeException(e);
        }
    }

    public void setBPSpeciesResource(String f) {
        speciesResource = f;
        assert typeManager != null;
        try {
            ABPSpeciesManagerReader sr = new ABPSpeciesManagerReader(speciesResource);
            speciesManager = sr.initializeSpeciesManager(typeManager);
        } catch (Exception e) {
            System.out.println("Unable to initialize species manager");
            throw new RuntimeException(e);
        }
    }
    
    public void setEncounterEnvironmentResource(String f) {
        environmentResource = f;
        try {
            AEncounterEnvironmentManagerReader er = new AEncounterEnvironmentManagerReader(environmentResource);
            environmentManager = er.initializeEnvironmentManager();
        } catch (Exception e) {
            System.out.println("Unable to initialize environment manager");
            throw new RuntimeException(e);
        }
    }
    
    public void setBPTypeResource(String f) {
        typeResource = f;
        try {
            ABPTypeManagerLoader tr = new ABPTypeManagerLoader(typeResource);
            typeManager = tr.loadManager();
        } catch (Exception e) {
            System.out.println("Unable to initialize type manager");
            throw new RuntimeException(e);
        }
    }
}
