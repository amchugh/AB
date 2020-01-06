package GameControl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;

public class ASceneEncounterTest {

    @Mock
    private APlayerCharacter mockPlayer;

    @Mock
    private ABP mockBP;

    @Mock
    private ABPSpecies mockSpecies;

    @Mock
    private AEnemy mockEnemy;

    @Mock
    private AEncounterEnvironment mockEnv;

    @Mock
    private AEncounterController encounterController;

    @InjectMocks
    private AEncounterInstance encI = new AEncounterInstance(0, mockEnv, mockEnemy);

    @InjectMocks
    private ASceneEncounter se = new ASceneEncounter(mockPlayer, encI, encounterController);

    @Mock
    private FontMetrics fontMetrics;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void verifySpeciesAreConsultedForImages() {
        Graphics2D g = Mockito.mock(Graphics2D.class);
        FontRenderContext frc = Mockito.mock(FontRenderContext.class);
        Mockito.when(g.getFontRenderContext()).thenReturn(frc);
        Font f = Mockito.mock(Font.class);
        Mockito.when(g.getFont()).thenReturn(f);
        GlyphVector pleasefortheloveofgodihatewritingunittests = Mockito.mock(GlyphVector.class);
        Mockito.when(f.createGlyphVector(any(), anyString())).thenReturn(pleasefortheloveofgodihatewritingunittests);
        Rectangle whyyyyyyyyyyyyyyyyyyyyyyyyyyyynooooooooooooooooooooo = Mockito.mock(Rectangle.class);
        Mockito.when(pleasefortheloveofgodihatewritingunittests.getPixelBounds(isNull(), anyFloat(), anyFloat())).thenReturn(whyyyyyyyyyyyyyyyyyyyyyyyyyyyynooooooooooooooooooooo);
        Mockito.when(whyyyyyyyyyyyyyyyyyyyyyyyyyyyynooooooooooooooooooooo.getHeight()).thenReturn(104101108112d);
        Mockito.when(whyyyyyyyyyyyyyyyyyyyyyyyyyyyynooooooooooooooooooooo.getWidth()).thenReturn(104101108112d);
        Mockito.when(mockBP.getSpecies()).thenReturn(mockSpecies);
        Mockito.when(mockEnemy.getActiveBP()).thenReturn(mockBP);
        Mockito.when(mockPlayer.getActiveBP()).thenReturn(mockBP);
        Mockito.when(mockBP.getName()).thenReturn("");
        Mockito.when(g.getFontMetrics(any())).thenReturn(fontMetrics);
        Mockito.when(fontMetrics.getAscent()).thenReturn(10);
        Mockito.when(fontMetrics.getHeight()).thenReturn(10);
        Mockito.when(fontMetrics.stringWidth(any())).thenReturn(10);
        ArrayList<ABPAction> act = new ArrayList<>();
        act.add(new ABPAction(0, new ABPType(0), "test", new ABPActionEffect[0]));
        Mockito.when(mockBP.getActions()).thenReturn(act);
        se.setup();
        se.draw(g);
        Mockito.verify(mockSpecies).getFrontImage();
        Mockito.verify(mockSpecies).getBackImage();
    }


}
