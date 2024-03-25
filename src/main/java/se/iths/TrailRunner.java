package se.iths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TrailRunner {
    //ArrayList för att lagra TrailSession-objekt.
    //HashMap för att lagra TrailSession-objekt med identifierare för snabb åtkomst.
    private ArrayList<TrailSession> trailSessions;
    private Map<String, TrailSession> identifierMap;

    //Användarens höjd och vikt som används för BMI-beräkningar.
    private int userHeight; 
    private int userWeight; 

    //Konstruktor för att skapa en instans av TrailRunner med en tom lista och tom karta.
    public TrailRunner() {
        trailSessions = new ArrayList<>();
        identifierMap = new HashMap<>();
    }
    
    //Sparar en TrailSession, genererar en unik identifierare och lägger till i listan och kartan.
    public void saveTrailSession(TrailSession session) {
        validateTrailSession(session);

        String identifier = generateIdentifier();
        session.setIdentifier(identifier);
        trailSessions.add(session);
        identifierMap.put(identifier, session);
    }

    //Validerar att avstånd och tid i en TrailSession är större än noll.
    private void validateTrailSession(TrailSession session) {
        if (session.getDistance() <= 0 || session.getTime().getTotalSeconds() <= 0) {
            throw new IllegalArgumentException("Distance and time must be greater than zero.");
        }
    }

    //Returnerar antalet sparade TrailSession-objekt.
    public int getTotalSessions() {
        return trailSessions.size();
    }

    //Sätter användarens höjd och vikt för BMI-beräkningar.
    public void setUserData(int height, int weight) {
        this.userHeight = height;
        this.userWeight = weight;
    }

    //Beräknar och returnerar BMI baserat på användarens höjd och vikt.
    public double calculateBMI() {
        double heightInMeter = userHeight / 100.0;
        return userWeight / (heightInMeter * heightInMeter);
    }

    //Beräknar och returnerar totalt avstånd från alla TrailSession-objekt.
    public double calculateTotalDistance() {
        return trailSessions.stream().mapToDouble(TrailSession::getDistance).sum();
    }

    //Beräknar och returnerar genomsnittligt avstånd från alla TrailSession-objekt.
    public double calculateAverageDistance() {
        return trailSessions.isEmpty() ? 0.0 : calculateTotalDistance() / trailSessions.size();
    }

    //Returnerar detaljer för en specifik TrailSession baserat på identifierare.
    public String printTrailSessionDetails(String identifier) {
        TrailSession session = identifierMap.get(identifier);
        if (session != null) {
            return session.toString();
        }
        throw new IllegalArgumentException("Invalid identifier.");
    }

    //Tar bort en TrailSession baserat på identifierare och returnerar true om det var framgångsrikt.
    public boolean deleteTrailSession(String identifier) {
        TrailSession session = identifierMap.remove(identifier);
        return session != null && trailSessions.remove(session);
    }

    //Genererar en unik identifierare för TrailSession-objekt.
    private String generateIdentifier() {
        String identifier;
        do {
            identifier = java.util.UUID.randomUUID().toString();
        } while (identifierMap.containsKey(identifier));
        return identifier;
    }
}
