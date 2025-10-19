package org.hbrs.se1.ws25.exercises.uebung3.persistence;


import java.io.Serializable;

public class ConcreteMember implements Member, Serializable {

    static private int nextID = 0;
    private int id;

    public ConcreteMember(){
        this.id = nextID++;
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public String toString(){
        return "Member (ID = " + this.getID() + ")";
    }
}
