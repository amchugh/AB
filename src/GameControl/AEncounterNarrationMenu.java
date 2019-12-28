package GameControl;

import java.awt.*;

public class AEncounterNarrationMenu extends AMenuManager {
    
    private final String[] texts;
    private int current;
    
    public AEncounterNarrationMenu(String text) {
        this(new String[]{text});
    }
    
    public AEncounterNarrationMenu(String[] texts) {
        super();
        this.texts = texts;
        current = -1;
        showNext();
    }
    
    public void showNext() {
        current++;
        displayItems.clear();
        displayItems.add(new AMenuNarrationItem(texts[current], 100, 100, 300, 200, Color.blue, Color.green));
    }
    
    public boolean hasMoreTexts() {
        return texts.length-1 > current;
    }

}
