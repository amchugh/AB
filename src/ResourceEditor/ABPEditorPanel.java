package ResourceEditor;

import GameControl.ABP;
import GameControl.ABPSpecies;
import GameControl.AResourceManager;
import GameControl.AStats;

import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ABPEditorPanel extends JPanel {
    
    // todo:: add a check to ensure a valid number has been entered for the XP value
    
    JTextField name;
    JTextField xp;
    JComboBox species = new JComboBox();
    
    private JTextField hp, as, ad, ap, app, sp, ed;
    
    private AResourceManager<ABPSpecies> speciesManager;
    
    public ABPEditorPanel(AResourceManager<ABPSpecies> speciesManager) {
        super();
        this.speciesManager = speciesManager;
        // Setup the layout. We're using box layout with a vertical axis so they stack nicely
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        Dimension d;
        
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
        Dimension buffer = new Dimension(0, 10);
        add(new Box.Filler(buffer, buffer, buffer));
        
        // todo:: add text and fields for stats
        addText("Stats");
        addStats(); // broke this out into its own method so it can be collapsed in the IDE
        
        add(Box.createVerticalGlue());
        addText("Bottom Text");
    }
    
    private void addStats() {
        JPanel col = new JPanel();
        col.setLayout(new GridLayout(7, 2));
        col.setAlignmentX(Component.LEFT_ALIGNMENT);
        hp = addOneStat(col, "HP");
        as = addOneStat(col, "Armor Strength");
        ad = addOneStat(col, "Armor Durability");
        ap = addOneStat(col, "Attack Power");
        app= addOneStat(col, "Attack Pierce");
        sp = addOneStat(col, "Speed");
        ed = addOneStat(col, "Endurance");
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
        add(col);
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
    
    public String[] getAllSpeciesNames() {
        String[] r = new String[speciesManager.getNumberOfItems()];
        java.util.List<ABPSpecies> species = speciesManager.getItems();
        for (int i = 0; i < species.size(); i++) {
            r[i] = species.get(i).getName();
        }
        return r;
    }
    
    public void loadBP(ABP b) {
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
        return bp;
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
    
    private int getValueFromField(JTextField f, String name) {
        try {
            return Integer.parseInt(f.getText());
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
