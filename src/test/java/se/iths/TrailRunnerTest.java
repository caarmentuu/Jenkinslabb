package se.iths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrailRunnerTest {
    private TrailRunner trailRunner;
    private TrailSession session;

    @Test
    public void testSaveTrailSession() {
        trailRunner = new TrailRunner();
        session = new TrailSession(10, new Time(0, 30, 0));
        trailRunner.saveTrailSession(session);

        session = new TrailSession(8, new Time(0, 25, 0), "2024-01-05");
        trailRunner.saveTrailSession(session);

        assertEquals(2, trailRunner.getTotalSessions());
    }

    @Test
    public void testSaveTrailSessionWithMissingData() {
        trailRunner = new TrailRunner();
        session = new TrailSession(); 

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            trailRunner.saveTrailSession(session);
        });

        assertEquals("Distance and time must be greater than zero.", exception.getMessage());
    }

    @Test
    public void testUniqueIdentifiers() {
        trailRunner = new TrailRunner();
        TrailSession session1 = new TrailSession(5, new Time(0, 20, 0));
        TrailSession session2 = new TrailSession(7, new Time(0, 35, 0));

        trailRunner.saveTrailSession(session1);
        trailRunner.saveTrailSession(session2);

        assertNotEquals(session1.getIdentifier(), session2.getIdentifier());
    }

    @Test
    public void testCalculateMetrics() {
        trailRunner = new TrailRunner();
        session = new TrailSession(10, new Time(1, 0, 0));
        trailRunner.saveTrailSession(session);

        assertEquals(10.0, session.getAverageSpeed(), 0.01);
        assertEquals(6.0, session.getKilometerTime(), 0.01);
    }

    @Test
    public void testCalculateBMI() {
        trailRunner = new TrailRunner();
        trailRunner.setUserData(175, 70);

        assertEquals(22.86, trailRunner.calculateBMI(), 0.01);
    }

    @Test
    public void testTotalDistance() {
        trailRunner = new TrailRunner();
        trailRunner.saveTrailSession(new TrailSession(5, new Time(0, 25, 0)));
        trailRunner.saveTrailSession(new TrailSession(8, new Time(0, 40, 0)));

        assertEquals(13.0, trailRunner.calculateTotalDistance(), 0.01);
    }

    @Test
    public void testAverageDistance() {
        trailRunner = new TrailRunner();
        trailRunner.saveTrailSession(new TrailSession(5, new Time(0, 25, 0)));
        trailRunner.saveTrailSession(new TrailSession(8, new Time(0, 40, 0)));

        assertEquals(6.5, trailRunner.calculateAverageDistance(), 0.01);
    }

    @Test
    public void testPrintTrailDetails() {
        trailRunner = new TrailRunner();
        session = new TrailSession(7, new Time(0, 35, 0));
        trailRunner.saveTrailSession(session);

        assertEquals(session.toString(), trailRunner.printTrailSessionDetails(session.getIdentifier()));
    }

    @Test
    public void testDeleteTrailSession() {
        trailRunner = new TrailRunner();
        session = new TrailSession(7, new Time(0, 35, 0));
        trailRunner.saveTrailSession(session);

        assertTrue(trailRunner.deleteTrailSession(session.getIdentifier()));
        assertEquals(0, trailRunner.getTotalSessions());
    }

    @Test
    public void testDeleteInvalidTrailSession() {
        trailRunner = new TrailRunner();
        assertFalse(trailRunner.deleteTrailSession("invalid_identifier"));
    }
}
