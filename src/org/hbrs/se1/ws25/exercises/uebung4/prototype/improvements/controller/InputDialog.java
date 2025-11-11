package org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.controller;

import org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.model.Container;
import org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.model.ContainerException;
import org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.model.UserStory;
import org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.view.UserStoryView;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputDialog {

    private final Container container = Container.getInstance();

    public void startEingabe() {
        Locale.setDefault(Locale.GERMANY);
        Scanner scanner = new Scanner(System.in);

        System.out.println("UserStory-Tool V1.2 (CLI). Tippe 'help' für Befehle.");

        while (true) {
            System.out.print("> ");

            // Falls stdin geschlossen ist (IDE ohne Terminal), nicht crashen:
            if (!scanner.hasNextLine()) {
                // Optional: kurze Info und erneut versuchen, statt zu beenden:
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                continue;
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();

            try {
                switch (cmd) {
                    case "help":
                        printHelp();
                        break;

                    case "dump": {
                        String projectFilter = (parts.length > 1) ? parts[1] : null;
                        new UserStoryView().startAusgabe(container.getCurrentList(), projectFilter);
                        break;
                    }

                    case "enter":
                        enterInteractive(scanner);
                        break;

                    case "store":
                        container.store();
                        break;

                    case "load":
                        container.load();
                        break;

                    case "exit":
                        System.out.println("Bye.");
                        return; // einzig erlaubter Ausstieg

                    default:
                        System.out.println("Unbekannter Befehl. Tipp: help");
                }
            } catch (ContainerException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("Eingabefehler: " + e.getMessage());
            } catch (NoSuchElementException e) {
                // Falls Eingabe abrupt endet (z. B. EOF), einfach weiter warten:
                System.out.println("(Keine Eingabequelle gefunden. Läuft weiter …)");
            } catch (Exception e) {
                System.out.println("Unerwarteter Fehler: " + e.getMessage());
            }
        }
    }

    private void printHelp() {
        System.out.println("Befehle:");
        System.out.println("  enter            - Eingabe einer User Story (Dialog)");
        System.out.println("  store            - Persistentes Speichern");
        System.out.println("  load             - Laden");
        System.out.println("  dump [projekt]   - Ausgabe (sortiert nach Prio), optional Projekt-Filter");
        System.out.println("  exit             - Beenden");
        System.out.println("  help             - Hilfe");
    }

    private void enterInteractive(Scanner sc) throws ContainerException {
        UserStory us = new UserStory();

        System.out.print("ID (int, unique): ");
        us.setId(readInt(sc));

        System.out.print("Titel (kurz): ");
        us.setTitel(sc.nextLine().trim());

        System.out.print("Projekt: ");
        us.setProject(sc.nextLine().trim());

        int mehrwert = readNonNegativeInt(sc, "Mehrwert (>=0): ");
        int strafe   = readNonNegativeInt(sc, "Strafe (>=0): ");
        int risk     = readNonNegativeInt(sc, "Risk (>=0): ");
        int aufwand  = readPositiveInt(sc,    "Aufwand (>0): ");

        us.setKpis(mehrwert, strafe, aufwand, risk); // berechnet prio
        container.addUserStory(us);
        System.out.printf(java.util.Locale.US, "OK. Priorität = %.3f%n", us.getPrio());
    }

    private int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.print("Bitte Ganzzahl eingeben: "); }
        }
    }
    private int readNonNegativeInt(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < 0) { System.out.println("Wert muss >= 0 sein."); continue; }
                return v;
            } catch (NumberFormatException e) { System.out.println("Bitte Ganzzahl eingeben."); }
        }
    }
    private int readPositiveInt(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v <= 0) { System.out.println("Wert muss > 0 sein."); continue; }
                return v;
            } catch (NumberFormatException e) { System.out.println("Bitte Ganzzahl eingeben."); }
        }
    }
}
