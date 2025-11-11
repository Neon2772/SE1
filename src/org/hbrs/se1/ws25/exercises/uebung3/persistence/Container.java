package org.hbrs.se1.ws25.exercises.uebung3.persistence;
import java.util.ArrayList;

public class Container {
    // Singleton Pattern, um immer nur eine Instanz dieser Klasse zu haben
    // Dadurch würden alle Klassen, die einen Container nutzen, auf den SELBEN Container zugreifen
    ArrayList<Member> memberList;
    private PersistenceStrategy<Member> strategy;
    // Der eine Container wird zur Klassen-Variable und ist damit keine Instanz-Variable, also gibt es den Container
    // immer nur genau ein Mal!
    private static Container container = new Container();

    // Container kann jetzt nur noch innerhalb der eigenen Klasse erstellt werden
    private Container(){
        this.memberList = new ArrayList();
    }

    // "Ersetzt" den Konstruktor. Bestellt die Klassenvariable "container" bereit
    public static Container getContainerInstance(){
        return container;
    }

    public void setPersistenceStrategy(PersistenceStrategy<Member> strategy) {
        this.strategy = strategy;
    }

    public void addMember(Member member) throws ContainerException {

        if (member == null) {
            throw new NullPointerException("Member darf nicht null sein!");
        }

        if(memberList.contains(member))  {
            throw new ContainerException(member.getID());
        }

        else {
            this.memberList.add(member);
        }
    }

    public String deleteMember(int id) {
        for(Member m : memberList) {
            if(m.getID() == id) {
                memberList.remove(m);
                return "Lösche Member mit ID: " + id;
            }
        }
        return "Member nicht Teil der Memberliste";

        /*
         * Statement zur Fehlerbehandlung:
         * Ein Nachteil ist die Unterscheidung von Fehlern. In diesem Beispiel wird
         * für jeden Fehler die selbe Fehlermeldung ausgegeben. Das macht es unmöglich
         * die genaue Ursache festzustellen. Darüber hinaus erschwert diese Art der
         * Fehlermeldung die Wartbarkeit, bzw. ein Programm wird leichter zu warten,
         * wenn man Exceptions abfängt.
         */

    }

    public int size(){
        return memberList.size();
    }

    public ArrayList<Member> getCurrentList(){
        return memberList;
    }

    public void store() throws PersistenceException {
        if (strategy == null) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.NoStrategyIsSet,
                    "No PersistenceStrategy is set!"
            );
        }
        strategy.save(memberList);
    }

    public void load() throws PersistenceException {
        if (strategy == null) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.NoStrategyIsSet,
                    "No PersistenceStrategy is set!"
            );
        }
        memberList = (ArrayList<Member>) strategy.load();
    }

    // Hilfsmethode zum Testen
    public void deleteAllMembers() {
        memberList.clear();
    }
}