package ResourceEditor.BPEditor;

import GameControl.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ABPEditorPanel extends JPanel {
    
    // todo:: add a check to ensure a valid number has been entered for the XP value

    JLabel selected;

    JTextField name;
    JTextField xp;
    JComboBox species = new JComboBox();

    JComboBox[] actionSelections;
    
    private JTextField hp, as, ad, ap, app, sp, ed;
    
    private AResourceManager<ABPSpecies> speciesManager;
    private AResourceManager<ABPAction> actionManager;

    public ABPEditorPanel(AResourceManager<ABPSpecies> speciesManager, AResourceManager<ABPAction> actionManager) {
        super();
        this.speciesManager = speciesManager;
        this.actionManager = actionManager;
        // Setup the layout. We're using box layout with a vertical axis so they stack nicely
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension buffer = new Dimension(0, 10);
        Dimension d;

        // We should add text component to show the current selected index
        selected = new JLabel("ID: [NONE]");
        add(selected);

        // Add a little buffer
        add(new Box.Filler(buffer, buffer, buffer));

        // Add a section for the Custom Name
        addText("Custom Name");
        name = new JTextField(1);
        stretchComponent(name);
        add(name);
        
        // Add a dropdown menu for the species
        addText("Species");
        String[] speciesNames = getAllSpeciesNames();
        species = new JComboBox(speciesNames);
        stretchComponent(species);
        add(species);
    
        // Add a section for the XP
        addText("XP");
        xp = new JTextField(1);
        stretchComponent(xp);
        add(xp);
        
        // Add a little buffer to the stats
        add(new Box.Filler(buffer, buffer, buffer));
        
        // Add a field for stats
        addStats(); // broke this out into its own method so it can be collapsed in the IDE

        // Add another buffer
        add(new Box.Filler(buffer, buffer, buffer));

        // Now we need to add a section for the Actions
        addText("Actions");
        String[] actionChoices = getActionChoices();
        int max = ABP.MAX_ACTIONS;
        actionSelections = new JComboBox[max];
        for (int i = 0; i < max; i++) {
            JComboBox b = new JComboBox(actionChoices);
            stretchComponent(b);
            add(b);
            actionSelections[i] = b;
        }

        add(Box.createVerticalGlue());
        addText("Bottom Text");
    }
    
    private void addStats() {
        JPanel col = new JPanel();
        col.setLayout(new GridLayout(7, 2));
        col.setAlignmentX(Component.LEFT_ALIGNMENT);
        col.setBorder(BorderFactory.createTitledBorder("Stats"));
        hp = addOneStat(col, "HP");
        as = addOneStat(col, "Armor Strength");
        ad = addOneStat(col, "Armor Durability");
        ap = addOneStat(col, "Attack Power");
        app= addOneStat(col, "Attack Pierce");
        sp = addOneStat(col, "Speed");
        ed = addOneStat(col, "Endurance");
        add(col);
        Dimension m = col.getPreferredSize();
        m.width = Integer.MAX_VALUE;
        col.setMaximumSize(m);
    }
    
    private JTextField addOneStat(JPanel col, String name) {
        Dimension filler = new Dimension(10, 0);
        JTextField stat;
        col.add(new JLabel(name + ":"));
        col.add(new Box.Filler(filler, filler, filler));
        stat = new JTextField();
        stretchComponent(stat);
        col.add(stat);
        return stat;
    }
    
    private void addText(String text) {
        JLabel l = new JLabel(text);
        add(l);
    }
    
    private void stretchComponent(JComponent c) {
        Dimension d = c.getPreferredSize();
        d.width = Integer.MAX_VALUE;
        c.setMaximumSize(d);
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private String[] getActionChoices() {
        // We are adding an extra option at the top. This will be our "No Action" choice
        String[] r = new String[actionManager.getNumberOfItems()+1];
        r[0] = "[NONE]";
        java.util.List<ABPAction> species = actionManager.getItems();
        for (int i = 0; i < species.size(); i++) {
            r[i+1] = species.get(i).getName();
        }
        return r;
    }

    private String[] getAllSpeciesNames() {
        String[] r = new String[speciesManager.getNumberOfItems()];
        java.util.List<ABPSpecies> species = speciesManager.getItems();
        for (int i = 0; i < species.size(); i++) {
            r[i] = species.get(i).getName();
        }
        return r;
    }
    
    public void loadBP(ABP b, int selectedNum) {
        selected.setText("ID: " + String.valueOf(selectedNum));
        String n = "";
        if (b.hasCustomName()) n = b.getName();
        name.setText(n);
        species.setSelectedIndex(b.getSpecies().getID());
        AStats s = b.getPersonalStats();
        xp.setText(String.valueOf(b.getXP()));
        hp.setText(String.valueOf(s.hitpoints));
        as.setText(String.valueOf(s.armorStrength));
        ad.setText(String.valueOf(s.armorDurability));
        ap.setText(String.valueOf(s.attackPower));
        app.setText(String.valueOf(s.attackPierce));
        sp.setText(String.valueOf(s.speed));
        ed.setText(String.valueOf(s.endurance));
        ArrayList<ABPAction> actions;
        try {
            actions = b.getActions();
        } catch (RuntimeException e) {
            actions = new ArrayList<ABPAction>();
        }
        for (int i = 0; i < actions.size(); i++) {
            int index = actions.get(i).getID()+1; // Moving over by one to account for our No move option
            actionSelections[i].setSelectedIndex(index);
        }
        for (int i = actions.size(); i < ABP.MAX_ACTIONS; i++) {
            actionSelections[i].setSelectedIndex(0);
        }
    }
    
    public ABP exportBP() {
        ABPSpecies spec = speciesManager.getItemByID(species.getSelectedIndex()); // todo:: get the actual species
        AStats s = exportStats();
        int xp = exportXP();
        ABP bp = new ABP(spec, s, xp);
        String n = name.getText();
        if (!n.equals("")) {
            bp.setCustomName(n);
        }
        // Now we need to set moves
        addMoves(bp);
        return bp;
    }

    private void addMoves(ABP bp) {
        for (int i = 0; i < actionSelections.length; i++) {
            int actionID = actionSelections[i].getSelectedIndex() - 1;
            if (actionID == -1) {
                // We have selected the "no action" action. We don't add this action to the bp.
            } else {
                bp.addAction(actionManager.getItemByID(actionID));
            }
        }
    }
    
    private AStats exportStats() {
        AStats s = new AStats();
        s.hitpoints = getValueFromField(hp, "HP");
        s.armorStrength = getValueFromField(as, "AS");
        s.armorDurability = getValueFromField(ad, "AD");
        s.attackPower = getValueFromField(ap, "AP");
        s.attackPierce = getValueFromField(app, "APP");
        s.speed = getValueFromField(sp, "SP");
        s.endurance = getValueFromField(ed, "ED");
        return s;
    }
    
    private double getValueFromField(JTextField f, String name) {
        try {
            return Double.parseDouble(f.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Failed to parse data from " + name);
            return 0;
        }
    }
    
    private int exportXP() {
        try {
            return Integer.parseInt(xp.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid XP. XP set to 0");
            return 0;
        }
    }
    
}
