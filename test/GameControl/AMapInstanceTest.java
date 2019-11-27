package GameControl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;

class AMapInstanceTest {

    @Mock
    private ACellManager cellManager;

    @Mock
    private AViewAdvisor viewAdvisor;

    @InjectMocks
    private AMapInstance map = new AMapInstance(1, 10, 10, cellManager);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private AViewAdvisor.ViewableGridCell constructFakeViewableGridCell(int screenCoordinateX, int screenCoordinateY,
                                                                        int gridX, int gridY) {
        return new AViewAdvisor.ViewableGridCell(
                new AViewAdvisor.LogicalScreenCoordinate(screenCoordinateX, screenCoordinateY),
                new GridPos(gridX, gridY)
        );
    }

    @Test
    void cellManagerNotUtilizedUntilMapInstanceIsExercised() {
        Mockito.verifyNoInteractions(cellManager);
    }

    @Test
    void cellManagerIsConsultedWhenDraw() {
        map.setViewAdvisor(viewAdvisor);

        GridPos desiredCenter = new GridPos(5, 5);
        Mockito.when(cellManager.getCellHeight()).thenReturn(16);
        Mockito.when(cellManager.getCellWidth()).thenReturn(16);

        java.util.List<AViewAdvisor.ViewableGridCell> fakeViewableResult = new ArrayList<>();
        fakeViewableResult.add(constructFakeViewableGridCell(0, 0, 5, 5));

        Mockito.when(viewAdvisor.advise(any(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(
                fakeViewableResult);

        Graphics g = Mockito.mock(Graphics.class);
        map.draw(g);
        Mockito.verify(g).drawImage(any(), eq(0), eq(0), eq(16), eq(16), any());
        Mockito.verifyNoMoreInteractions(g);
    }
}