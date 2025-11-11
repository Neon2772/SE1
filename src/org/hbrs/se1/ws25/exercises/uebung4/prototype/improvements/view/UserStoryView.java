package org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.view;

import org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.model.UserStory;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UserStoryView {

    /**
     * Gibt eine sortierte (nach Priorität) und ggf. gefilterte Liste aus.
     * @param liste Liste aus dem Container
     * @param suchParameter Projekt-Filter (kann null sein). Wenn null/leer -> kein Projekt-Filter.
     */
    public void startAusgabe(List<UserStory> liste, String suchParameter) {

        if (liste == null || liste.isEmpty()) {
            System.out.println("(keine User Stories vorhanden)");
            return;
        }

        // Sortierung (nutzt Comparable: absteigend nach prio)
        Collections.sort(liste);

        boolean filterByProject = (suchParameter != null && !suchParameter.isBlank());

        List<UserStory> reduzierteListe = liste.stream()
                .filter(us -> !filterByProject || (us.getProject() != null && us.getProject().equals(suchParameter)))
                // Beispiel Filter: prio >= 0 (oder >= 3, wenn gewünscht)
                .filter(us -> us.getPrio() >= 0)
                .collect(Collectors.toList());

        if (reduzierteListe.isEmpty()) {
            System.out.println("(keine passenden User Stories für Filter \"" + suchParameter + "\")");
            return;
        }

        String header = String.format("%-6s | %-24s | %-10s | %7s | %7s | %7s | %7s | %8s",
                "ID", "Titel", "Projekt", "Mehrw.", "Strafe", "Risk", "Aufwand", "Prio");
        System.out.println(header);
        System.out.println("-".repeat(header.length()));

        reduzierteListe.forEach(us -> System.out.printf(Locale.US,
                "%-6d | %-24s | %-10s | %7d | %7d | %7d | %7d | %8.3f%n",
                us.getId(),
                truncate(us.getTitel(), 24),
                truncate(us.getProject(), 10),
                us.getMehrwert(), us.getStrafe(), us.getRisk(), us.getAufwand(),
                us.getPrio()));
    }

    private static String truncate(String s, int n) {
        if (s == null) return "";
        return s.length() <= n ? s : s.substring(0, Math.max(0, n - 1)) + "…";
    }
}
