package ResourceEditor;

import GameControl.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AResourceEditorMain {
    
    // Store the file locations for all the managers
    private String environmentResource;
    private String speciesResource;
    private String actionResource;
    private String typeResource;
    
    // We will need a ResourceManager for every resource in the game currently
    private AResourceManager<ABPType> typeManager = null;
    private AResourceManager<AEncounterEnvironment> environmentManager;
    private AResourceManager<ABPSpecies> speciesManager;
    private AResourceManager<ABPAction> actionManager;
    
    public void setBPActionResource(String f) {
        actionResource = f;
        if (typeManager == null) throw new RuntimeException("Type manager not initialized!");
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
        if (typeManager == null) throw new RuntimeException("Type manager not initialized!");
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
    
    // ----------------------------------
    // -- General methods              --
    // ----------------------------------
    
    public void setup() {
        loadBPs();
        handleDisplay();
        // Display the first BP (if it exists)
        if (loadedBPs.size() > 0) {
            currentSel = 0;
            display.selectNewBP(loadedBPs.get(0));;
        }
    }
    
    // ----------------------------------
    // -- Handling the current BPs     --
    // ----------------------------------
    
    private ArrayList<ABP> loadedBPs;
    
    private static ABP defaultBP;
    
    private void loadBPs() {
        defaultBP = new ABP(speciesManager.getItemByID(0), new AStats(), 0);
        loadedBPs = new ArrayList<>();
        
        // todo:: remove temp BP
        ABP bp = new ABP(speciesManager.getItemByID(0), new AStats(), 0);
        bp.setCustomName("foo");
        ABP bp2 = new ABP(speciesManager.getItemByID(1), new AStats(), 10);
        bp2.setCustomName("bar");
        loadedBPs.add(bp);
        loadedBPs.add(bp2);
    }
    
    private void exportBPs() {
    
    }
    
    public void addBP() {
        loadedBPs.add(defaultBP);
        // This BP is our new selected BP if it's our first one
        if (loadedBPs.size() == 1) {
            currentSel = 0;
            display.selectNewBP(loadedBPs.get(currentSel));
        } else {
            // This BP will still be our new selected, but let's go there nicely
            onSelectNewBP(loadedBPs.size()-1);
        }
        display.updateTableData(loadedBPs);
    }
    
    public void removeSelectedBP() {
        if (loadedBPs.size() > 0) {
            loadedBPs.remove(currentSel);
            display.updateTableData(loadedBPs);
            currentSel -= 1;
            if (currentSel < 0) {
                currentSel = 0; // don't go negative!
            }
            // Now we need to select a new BP!
            // 2) open the new BP
            display.selectNewBP(loadedBPs.get(currentSel));
        }
    }
    
    // ----------------------------------
    // -- Handle the display
    // ----------------------------------
    private AResourceEditorDisplay display;
    private ABPTableSelectionHandler handler;
    private int currentSel;
    
    public void handleDisplay() {
        handler = new ABPTableSelectionHandler(this);
        display = new AResourceEditorDisplay(handler, speciesManager);
        
        // Now, add a test BP to the display
        ABP test;
        try {
            test = new ABPReader(AGameMain.getBPDataFileNameFromID(0)).readABP(speciesManager, actionManager);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ArrayList<ABP> bpList = new ArrayList<>();
        bpList.add(test);
        display.updateTableData(loadedBPs);
    }
    
    public void onSelectNewBP() {
        onSelectNewBP(display.getSelectedRow());
    }
    
    public void onSelectNewBP(int newIndex) {
        if (newIndex < 0) {
            // We selected a column header...
            return;
        }
        // We need to do two things:
        // 1) update the edited BP
        loadedBPs.set(currentSel, display.getEditedBP());
        // 2) open the new BP
        display.selectNewBP(loadedBPs.get(newIndex));
        currentSel = newIndex;
        // Finally, update the table after exporting the new BP
        display.updateTableData(loadedBPs);
    }
    
}
