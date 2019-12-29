package ResourceEditor;

import GameControl.*;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class AResourceEditorMain {
    
    // Store the file locations for all the managers
    private String environmentResource;
    private String speciesResource;
    private String actionResource;
    private String typeResource;
    private String bpResources;
    
    // We will need a ResourceManager for every resource in the game currently
    private AResourceManager<ABPType> typeManager = null;
    private AResourceManager<AEncounterEnvironment> environmentManager;
    private AResourceManager<ABPSpecies> speciesManager;
    private AResourceManager<ABPAction> actionManager;
    
    public void setBPFileResource(String f) {
        bpResources = f;
    }
    
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
        
        // Parse the BP folder
        File dir = new File(bpResources);
        File[] files = dir.listFiles();
    
        for (int i = 0; i < files.length; i++) {
            try {
                ABP bp = new ABPReader(files[i].getAbsolutePath()).readABP(speciesManager, actionManager);
                loadedBPs.add(bp);
                loadedBPs.add(bp);
            } catch (IOException e) {
                System.out.println("Failed to load file " + files[i].getName());
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("Failed to parse file " + files[i].getName());
                e.printStackTrace();
            }
        }
    }
    
    public void exportBPs() {
        // We're going to delete every file currently in the folder.
        File dir = new File(bpResources);
        File[] files = dir.listFiles();
        
        // improveme:: unfortunately, it seems we are unable to delete files.
        // There is a Windows permission flaw that means we cannot delete it as it's "in-use"
        // UPDATE improveme:: I did some further testing. It appears it really only fails with committed files.
        // It worked fine with my temp files. I won't think about it too much.
        for (int i = 0; i < files.length; i++) {
            Path floc = files[i].toPath();
            try {
                Files.delete(floc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Now, we're going to create files for every BP in the current array
        for (int i = 0; i < loadedBPs.size(); i++) {
            // Find if the file already exists
            ABP bp = loadedBPs.get(i);
            File f;
            if (files.length > i) {
                // The file already exists
                f = files[i];
                // We will clear the file real quick
                try {
                    FileWriter fw = new FileWriter(f);
                    PrintWriter pw = new PrintWriter(fw, false);
                    pw.flush();
                    pw.close();
                    fw.close();
                } catch (IOException e) {
                    System.out.println("Failed to clean file #" + String.valueOf(i));
                }
            } else {
                f = new File(dir.getAbsolutePath() + "\\" + String.valueOf(i) + ".bpf");
            }
            // Now, we need to write all our data to the file.
            FileWriter fr = null;
            try {
                fr = new FileWriter(f);
                // Time to write our data!
                fr.write("{\n");
                fr.write("\t\"SpeciesID\": " + bp.getSpecies().getID() + ",\n");
                fr.write("\t\"XP\": " + bp.getXP() + ",\n");
                fr.write("\t\"Actions\": [\n");
                fr.write("\t\t");
                ArrayList<ABPAction> actions = bp.getActions();
                for (int j = 0; j < actions.size(); j++) {
                    int id = actions.get(j).getID();
                    fr.write(id + ", ");
                }
                fr.write("\n\t],\n");
                fr.write("\t\"Stats\": {\n");
                fr.write("\t\t\"HP\": " + bp.getPersonalStats().hitpoints + ",\n");
                fr.write("\t\t\"AS\": " + bp.getPersonalStats().armorStrength + ",\n");
                fr.write("\t\t\"AD\": " + bp.getPersonalStats().armorDurability + ",\n");
                fr.write("\t\t\"AP\": " + bp.getPersonalStats().attackPower + ",\n");
                fr.write("\t\t\"APP\": " +bp.getPersonalStats().attackPierce + ",\n");
                fr.write("\t\t\"SP\": " + bp.getPersonalStats().speed + ",\n");
                fr.write("\t\t\"ED\": " + bp.getPersonalStats().endurance + ",\n");
                fr.write("\t},\n");
                
                if (bp.hasCustomName()) {
                    fr.write("\t\"CustomName\": \"" + bp.getName() + "\",\n");
                }
                
                fr.write("\t\"CurrentHP\": " + bp.getSpecies().getMaxHealth() + ",\n"); // todo:: support not having full HP
                fr.write("}");
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Close the files
                try {
                    fr.close();
                } catch (IOException e) {
                    // swallow
                }
            }
        }
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
    
    public void updateCurrentBP() {
        loadedBPs.set(currentSel, display.getEditedBP());
    }
    
}
