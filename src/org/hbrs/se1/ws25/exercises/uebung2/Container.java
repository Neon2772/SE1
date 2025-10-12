package org.hbrs.se1.ws25.exercises.uebung2;
import java.util.ArrayList;

public class Container {
    ArrayList<Member> memberList;

    public Container(){
        this.memberList = new ArrayList();
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
                return "Deleted Member with ID: " + id;
            }
        }
        return "Member not part of the memberlist";

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
}
