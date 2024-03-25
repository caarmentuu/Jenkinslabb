package se.iths;

import java.time.LocalDate;

public class TrailSession {
    private String identifier;
    private double distance; 
    private Time time;
    private LocalDate date;

    public TrailSession() {
        setDateNow();
    }

    public TrailSession(double distance, Time time) {
        this.distance = distance;
        this.time = time;
        setDateNow();
    }

    public TrailSession(double distance, Time time, String date) {
        this.distance = distance;
        this.time = time;
        this.date = LocalDate.parse(date);
    }

    private void setDateNow() {
        this.date = LocalDate.now();
    }

    public double getDistance() {
        return distance;
    }

    public Time getTime() {
        return time;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getAverageSpeed() {
        return distance / time.getTotalHours();
    }

    public double getKilometerTime() {
        return time.getTotalMinutes() / distance;
    }

    @Override
    public String toString() {
        return "TrailSession{" +
                "identifier='" + identifier + '\'' +
                ", distance=" + distance +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}
