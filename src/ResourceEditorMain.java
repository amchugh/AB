import ResourceEditor.AResourceEditorMain;

public class ResourceEditorMain {
    
    // Resource File Locations
    private static final String DEFAULT_ENVIRONMENT_RESOURCE = "rsc/SimpleEnvironmentData.eef";
    private static final String DEFAULT_SPECIES_RESOURCE = "rsc/SimpleSpeciesData.sdf";
    private static final String DEFAULT_ACTION_RESOURCE = "rsc/SimpleActionData.adf";
    private static final String DEFAULT_TYPE_RESOURCE = "rsc/SimpleTypeData.tdf";
    private static final String DEFAULT_BP_RESOURCES = "rsc/BPFiles/";
    
    private static final String TEST_MAP_RESOURCE = "rsc/Maps/0.map";
    
    public static void main(String[] args) throws Exception {
    
        // Create the resource editor
        AResourceEditorMain main = new AResourceEditorMain();
    
        // Setup the locations for the resources
        String environmentResource = DEFAULT_ENVIRONMENT_RESOURCE;
        String speciesResource = DEFAULT_SPECIES_RESOURCE;
        String actionResource = DEFAULT_ACTION_RESOURCE;
        String typeResource = DEFAULT_TYPE_RESOURCE;
    
        // Give the resource locations to main
        main.setBPTypeResource(typeResource);
        main.setBPActionResource(actionResource);
        main.setBPSpeciesResource(speciesResource);
        main.setEncounterEnvironmentResource(environmentResource);
        main.setBPFileResource(DEFAULT_BP_RESOURCES);
        
        // Finally, allow to setup
        main.setup();
    }
}
