package org.hbrs.se1.ws25.exercises.uebung3.persistence;
import java.util.List;

public class MemberView {
    // Vergleichbar zu toString, aber fancier
    public void dump(List<Member> liste) {
        System.out.println("----- Dump der Member-Liste -----");
        for (Member m : liste) {
            System.out.println(m);
        }
        System.out.println("--------------------------------");
    }
}
