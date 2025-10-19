package org.hbrs.se1.ws25.exercises.uebung3;
import org.hbrs.se1.ws25.exercises.uebung3.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {

    private Container container;

    @BeforeEach
    void setup() {
        container = Container.getContainerInstance();
        container.deleteAllMembers();
        container.setPersistenceStrategy(null);
    }

    /**
     * Testfall 1: Keine Strategie gesetzt → store() soll Exception werfen
     */
    @Test
    void testNoStrategySet() {
        assertThrows(PersistenceException.class, () -> {
            container.store();
        });
    }

    /**
     * Testfall 2: Verwendung der MongoDB-Strategie → sollte Exception werfen
     */
    @Test
    void testMongoDBStrategyNotImplemented() {
        container.setPersistenceStrategy(new PersistenceStrategyMongoDB<>());
        assertThrows(PersistenceException.class, () -> {
            container.store();
        });
        assertThrows(PersistenceException.class, () -> {
            container.load();
        });
    }

    /**
     * Testfall 3: Fehlerhafte Location (ein Directory) → sollte Exception werfen
     */
    @Test
    void testWrongLocationForStream() throws ContainerException {
        PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
        strategy.setLocation("/tmp/"); // ungültig, da ein Directory
        container.setPersistenceStrategy(strategy);

        try {
            container.addMember(new ConcreteMember());
        } catch (ContainerException e) {
            throw new ContainerException(0);
        }


        assertThrows(PersistenceException.class, () -> {
            container.store();
        });
    }

    /**
     * Testfall 4: Round-Trip – speichern und laden
     */
    @Test
    void testRoundTrip() throws PersistenceException, ContainerException {
        // Strategie setzen
        PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
        strategy.setLocation("testObjects.ser");
        container.setPersistenceStrategy(strategy);

        // Container leeren, Member hinzufügen
        container.deleteAllMembers();
        Member m1 = new ConcreteMember();
        container.addMember(m1);

        // Schritt 1: speichern
        container.store();
        assertEquals(1, container.size());

        // Schritt 2: Container leeren
        container.deleteAllMembers();
        assertEquals(0, container.size());

        // Schritt 3: laden
        container.load();
        List<Member> list = container.getCurrentList();
        assertEquals(1, list.size());
        assertEquals(m1.getID(), list.get(0).getID());
    }
}

