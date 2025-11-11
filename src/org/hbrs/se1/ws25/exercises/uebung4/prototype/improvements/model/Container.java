package org.hbrs.se1.ws25.exercises.uebung4.prototype.improvements.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Container {

    private static final Container instance = new Container();
    public static Container getInstance() { return instance; }

    private List<UserStory> liste;

    // persistenter Speicherort
    public static final String LOCATION = "allStories.ser";

    private Container() {
        this.liste = new ArrayList<>();
    }

    // --- Persistenz ---

    public void store() throws ContainerException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LOCATION))) {
            oos.writeObject(this.liste);
            System.out.println(this.size() + " UserStory(s) wurden erfolgreich gespeichert!");
        } catch (IOException e) {
            throw new ContainerException("Fehler beim Abspeichern");
        }
    }

    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(LOCATION))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<UserStory> loaded = (List<UserStory>) obj;
                this.liste = loaded; // überschreibt vorhandene Einträge
            }
            System.out.println("Es wurden " + this.size() + " UserStory(s) erfolgreich reingeladen!");
        } catch (FileNotFoundException e) {
            System.out.println("LOG: Datei nicht gefunden – noch nichts gespeichert?");
        } catch (IOException e) {
            System.out.println("LOG: IO-Fehler beim Laden: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("LOG: ClassNotFound beim Laden!");
        }
    }

    // --- RAM-Operationen ---

    public void addUserStory(UserStory userStory) throws ContainerException {
        if (userStory == null) throw new ContainerException("UserStory darf nicht null sein.");
        if (containsId(userStory.getId())) {
            ContainerException ex = new ContainerException("ID bereits vorhanden!");
            ex.addID(userStory.getId());
            throw ex;
        }
        liste.add(userStory);
    }

    private boolean containsId(int id) {
        for (UserStory us : liste) {
            if (us.getId() == id) return true;
        }
        return false;
    }

    public int size() { return liste.size(); }

    public List<UserStory> getCurrentList() { return this.liste; }

    /** optional für Tests */
    public void deleteAll() { this.liste.clear(); }

    /** optional: eine US holen */
    private UserStory getUserStory(int id) {
        for (UserStory us : liste) {
            if (us.getId() == id) return us;
        }
        return null;
    }
}
