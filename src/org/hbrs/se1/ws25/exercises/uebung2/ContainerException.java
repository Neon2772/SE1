package org.hbrs.se1.ws25.exercises.uebung2;

public class ContainerException extends Exception{

    public ContainerException(int memberID){
        super("Das Member-Objekt mit der ID " + memberID + " ist bereits vorhanden!");
    }
}
