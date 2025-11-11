package org.hbrs.se1.ws25.exercises.uebung3.persistence;
import java.io.*;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    // Methode um Memberliste von HDD zu lesen
    public void save(List<E> member) throws PersistenceException {
        if (location == null || location.isEmpty()) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "No valid file location defined!"
            );
        }
//         Try-with-Resources
//         Öffnet Ressourcen im Try(...) und schließt sie automatisch.
//         Dadurch ist kein Finaly{} nötig, in dem die Ressource geschlössen wird
//         Ist weniger Fehleranfällig
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(location))) {
            oos.writeObject(member);
        } catch (IOException e) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Error while saving objects: " + e.getMessage()
            );
        }
    }

    @Override
    // Methode um Memberliste auf HDD zu schreiben
    public List<E> load() throws PersistenceException {
        if (location == null || location.isEmpty()) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "No valid file location defined!"
            );
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(location))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<E> newList = (List<E>) obj;
                return newList;
            } else {
                throw new PersistenceException(
                        PersistenceException.ExceptionType.ImplementationNotAvailable,
                        "Loaded object is not of type List!"
                );
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "Error while loading objects: " + e.getMessage()
            );
        }
    }
}
