package org.hbrs.se1.ws25.exercises.uebung2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {

    private Container container;

    @BeforeEach
    void setUp() {
        container = new Container();
    }

    @Test
    void testAddSingleMember() throws ContainerException {
        Member m = new ConcreteMember();
        container.addMember(m);
        assertEquals(1, container.size());
    }

    @Test
    void testAddMultipleMembers() throws ContainerException {
        Member m1 = new ConcreteMember();
        Member m2 = new ConcreteMember();
        container.addMember(m1);
        container.addMember(m2);
        assertEquals(2, container.size());
    }

    @Test
    void testAddDuplicateMemberThrowsException() throws ContainerException {
        Member m1 = new ConcreteMember();
        container.addMember(m1);
        ContainerException ex = assertThrows(ContainerException.class, () -> container.addMember(m1));
        assertTrue(ex.getMessage().contains("ist bereits vorhanden"));
    }

    @Test
    void testDeleteExistingMemberReturnsMessage() throws ContainerException {
        Member m1 = new ConcreteMember();
        container.addMember(m1);
        String result = container.deleteMember(m1.getID());
        assertEquals("Deleted Member with ID: " + m1.getID(), result);
    }

    @Test
    void testDeleteNonExistingMemberReturnsErrorMessage() {
        String result = container.deleteMember(999);
        assertEquals("Member not part of the memberlist", result);
    }

    @Test
    void testSizeAfterAddAndDelete() throws ContainerException {
        Member m1 = new ConcreteMember();
        Member m2 = new ConcreteMember();
        container.addMember(m1);
        container.addMember(m2);
        container.deleteMember(m1.getID());
        assertEquals(1, container.size());
    }

    @Test
    void testDumpUsesToStringCorrectly() throws ContainerException {
        Member m1 = new ConcreteMember();
        container.addMember(m1);
        assertEquals("Member (ID = " + m1.getID() + ")", m1.toString());
    }

    @Test
    void testAddTenMembers() throws ContainerException {
        for (int i = 0; i < 10; i++) {
            container.addMember(new ConcreteMember());
        }
        assertEquals(10, container.size());
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(NullPointerException.class, () -> container.addMember(null));
    }

    @Test
    void testIndependentContainers() throws ContainerException {
        Container c1 = new Container();
        Container c2 = new Container();

        Member m1 = new ConcreteMember();
        Member m2 = new ConcreteMember();

        c1.addMember(m1);
        c2.addMember(m2);

        assertEquals(1, c1.size());
        assertEquals(1, c2.size());
        assertNotEquals(c1, c2);
    }
}

