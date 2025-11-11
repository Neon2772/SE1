package org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.model;

import java.io.Serializable;

public class UserStory implements Comparable<UserStory>, Serializable {

    private static final long serialVersionUID = 1L;

    private String titel;
    private int aufwand = 0;   // effort  (> 0)
    private int id = 0;        // unique
    private int mehrwert = 0;  // benefit  (>= 0)
    private int risk = 0;      // risk     (>= 0)
    private int strafe = 0;    // penalty  (>= 0)
    private double prio = 0.0; // (mehrwert + strafe + risk) / aufwand
    private String project;

    public UserStory() {}

    public UserStory(int id, String titel, int mehrwert, int strafe,
                     int aufwand, int risk) {
        this.id = id;
        this.titel = titel;
        setKpis(mehrwert, strafe, aufwand, risk); // berechnet prio
    }

    // --- Getter/Setter ---

    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }

    public double getPrio() { return prio; }
    /** prio wird berechnet – kein externer Setter nötig */
    private void setPrio(double prio) { this.prio = prio; }

    public String getTitel() { return titel; }
    public void setTitel(String titel) { this.titel = titel; }

    public int getAufwand() { return aufwand; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMehrwert() { return mehrwert; }
    public int getRisk() { return risk; }
    public int getStrafe() { return strafe; }

    public void setAufwand(int aufwand) { setKpis(this.mehrwert, this.strafe, aufwand, this.risk); }
    public void setMehrwert(int mehrwert) { setKpis(mehrwert, this.strafe, this.aufwand, this.risk); }
    public void setRisk(int risk) { setKpis(this.mehrwert, this.strafe, this.aufwand, risk); }
    public void setStrafe(int strafe) { setKpis(this.mehrwert, strafe, this.aufwand, this.risk); }

    /** Validiert Kennzahlen und berechnet die Priorität automatisch. */
    public void setKpis(int mehrwert, int strafe, int aufwand, int risk) {
        if (mehrwert < 0 || strafe < 0 || risk < 0) {
            throw new IllegalArgumentException("Mehrwert/Strafe/Risk müssen >= 0 sein.");
        }
        if (aufwand <= 0) {
            throw new IllegalArgumentException("Aufwand muss > 0 sein.");
        }
        this.mehrwert = mehrwert;
        this.strafe = strafe;
        this.aufwand = aufwand;
        this.risk = risk;
        // Gloger (einfach): (benefit + penalty + risk) / effort
        setPrio(((double) mehrwert + strafe + risk) / aufwand);
    }

    // --- Comparable: absteigend nach Prio ---
    @Override
    public int compareTo(UserStory other) {
        return Double.compare(other.getPrio(), this.getPrio());
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "titel='" + titel + '\'' +
                ", aufwand=" + aufwand +
                ", id=" + id +
                ", mehrwert=" + mehrwert +
                ", risk=" + risk +
                ", strafe=" + strafe +
                ", prio=" + String.format(java.util.Locale.US, "%.3f", prio) +
                ", project='" + project + '\'' +
                '}';
    }
}
