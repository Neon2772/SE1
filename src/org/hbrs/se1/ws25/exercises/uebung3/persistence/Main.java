package org.hbrs.se1.ws25.exercises.uebung3.persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Container container = Container.getContainerInstance();

        try {
            container.addMember(new ConcreteMember());
            container.addMember(new ConcreteMember());
            container.addMember(new ConcreteMember());
        } catch (ContainerException e) {
            System.out.println("Fehler: " + e.getMessage());
        }

        // Liste auslesen
        List<Member> currentList = container.getCurrentList();

        // Ausgabe über MemberView
        MemberView view = new MemberView();
        view.dump(currentList);
    }
}

