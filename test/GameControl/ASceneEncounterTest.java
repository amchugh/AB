package GameControl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;

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

    @InjectMocks
    private AEncounterInstance encI = new AEncounterInstance(0, mockEnv, mockEnemy);

    @InjectMocks
    private ASceneEncounter se = new ASceneEncounter(mockPlayer, encI);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void verifySpeciesAreConsultedForImages() {
        Graphics g = Mockito.mock(Graphics.class);
        Mockito.when(mockBP.getSpecies()).thenReturn(mockSpecies);
        Mockito.when(mockEnemy.getActiveBP()).thenReturn(mockBP);
        Mockito.when(mockPlayer.getActiveBP()).thenReturn(mockBP);
        se.draw(g);
        Mockito.verify(mockSpecies).getFrontImage();
        Mockito.verify(mockSpecies).getBackImage();
    }


}
